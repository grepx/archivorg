package gregpearce.archivorg.network;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import gregpearce.archivorg.Constants;
import gregpearce.archivorg.model.ArchiveEntity;
import gregpearce.archivorg.model.MediaType;
import gregpearce.archivorg.model.ResultPage;
import gregpearce.archivorg.util.NullUtil;
import retrofit2.Retrofit;
import rx.Observable;

@Singleton
class ArchiveOrgService {
  private ArchiveOrgApi archiveOrgApi;

  @Inject ArchiveOrgService(Retrofit retrofit) {
    archiveOrgApi = retrofit.create(ArchiveOrgApi.class);
  }

  public static final String REVIEW_DATE_DESC = "reviewdate desc";
  public static final String DOWNLOADS_DESC = "downloads desc";
  public static final String TOP_QUERY = "downloads:[2000 TO 100000000] AND avg_rating:[3 TO 5]";

  public Observable<ResultPage> search(String query, int page, String sort) {
    return archiveOrgApi.search(query, page, Constants.PAGE_SIZE, sort)
        // retry on network failure 3 times
        .retry(3)
        // map the network response to the domain model
        .map(apiResponse -> {
          SearchResponse.Response response = apiResponse.response;

          List<ArchiveEntity> results = new ArrayList<>();
          for (SearchResponse.Response.Doc doc : response.docs) {
            ArchiveEntity archiveEntity = ArchiveEntity.create(
                // archive.org data is full of nulls, protect against it where possible
                NullUtil.defaultNullValue(doc.title),
                NullUtil.defaultNullValue(doc.description),
                parseDate(doc.publicdate),
                parseMediaType(doc.mediatype)
            );
            results.add(archiveEntity);
          }

          boolean isLastPage = (response.numFound - response.start) <= Constants.PAGE_SIZE;

          return ResultPage.create(results, response.numFound, page, isLastPage);
        });
  }

  private LocalDateTime parseDate(String date) {
    // remove the Z that archive.org puts on the end of date strings
    date = date.substring(0, date.length()-1);
    return LocalDateTime.parse(date);
  }

  private MediaType parseMediaType(String mediaType) {
    if (mediaType.equals("video")) {
      return MediaType.Video;
    } else if (mediaType.equals("audio")) {
      return MediaType.Audio;
    } else if (mediaType.equals("texts")) {
      return MediaType.Book;
    } else if (mediaType.equals("image")) {
      return MediaType.Image;
    } else {
      return MediaType.Unknown;
    }
  }
}