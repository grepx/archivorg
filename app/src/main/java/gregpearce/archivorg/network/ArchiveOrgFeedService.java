package gregpearce.archivorg.network;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import gregpearce.archivorg.Constants;
import gregpearce.archivorg.model.FeedItem;
import gregpearce.archivorg.model.MediaType;
import gregpearce.archivorg.model.ResultPage;
import gregpearce.archivorg.util.NullUtil;
import rx.Observable;

@Singleton
class ArchiveOrgFeedService {
  private ArchiveOrgApiV1 api;

  @Inject ArchiveOrgFeedService(ArchiveOrgApiV1 api) {
    this.api = api;
  }

  public static final String REVIEW_DATE_DESC = "publicdate desc";
  public static final String DOWNLOADS_DESC = "downloads desc";
  public static final String TOP_QUERY = "downloads:[2000 TO 100000000] AND avg_rating:[3 TO 5]";

  public Observable<ResultPage> search(String query, int page, String sort) {
    if (query.isEmpty()) {
      throw new RuntimeException("Archive.org api does not accept empty search parameter");
    }
    return api.search(query, page, Constants.PAGE_SIZE, sort)
        // retry on network failure 3 times
        .retry(3)
        // map the network response to the domain model
        .map(apiResponse -> {
          FeedResponse.Response response = apiResponse.response;

          List<FeedItem> results = new ArrayList<>();
          for (FeedResponse.Response.Doc doc : response.docs) {
            FeedItem feedItem = FeedItem.create(
                // archive.org data is full of nulls, protect against it where possible
                NullUtil.defaultNullValue(doc.title),
                NullUtil.defaultNullValue(doc.description),
                parseDate(doc),
                parseMediaType(doc)
            );
            results.add(feedItem);
          }

          boolean isLastPage = (response.numFound - response.start) <= Constants.PAGE_SIZE;

          return ResultPage.create(results, response.numFound, page, isLastPage);
        });
  }

  private LocalDateTime parseDate(FeedResponse.Response.Doc doc) {
    // remove the Z that archive.org puts on the end of date strings
    String date = doc.publicdate;
    if (date == null) {
      return null;
    }
    date = date.substring(0, date.length() - 1);
    return LocalDateTime.parse(date);
  }

  private MediaType parseMediaType(FeedResponse.Response.Doc doc) {
    if ("movies".equals(doc.mediatype)) {
      return MediaType.Video;
    } else if ("audio".equals(doc.mediatype) || "sound".equals(doc.type)) {
      return MediaType.Audio;
    } else if ("texts".equals(doc.mediatype)) {
      return MediaType.Book;
    } else if ("image".equals(doc.mediatype)) {
      return MediaType.Image;
    } else {
      return MediaType.Unknown;
    }
  }
}