package gregpearce.archivorg.data.network;

import gregpearce.archivorg.domain.Constants;
import gregpearce.archivorg.domain.model.FeedItem;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.util.NullUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import timber.log.Timber;

@Singleton class ArchiveOrgFeedService {

  private ArchiveOrgApiV1 api;

  @Inject ArchiveOrgFeedService(ArchiveOrgApiV1 api) {
    this.api = api;
  }

  public static final String REVIEW_DATE_DESC = "publicdate desc";
  public static final String DOWNLOADS_DESC = "downloads desc";
  public static final String TOP_QUERY = "downloads:[10000 TO 999999999] AND avg_rating:[3 TO 5]";

  public static String getFeedTypeClause(FeedContentType feedContentType) {
    switch (feedContentType) {
      case Audio:
        return " AND mediatype:(audio)";
      case Book:
        return " AND mediatype:(texts)";
      case Image:
        return " AND mediatype:(image)";
      case Video:
        return " AND mediatype:(movies)";
      default:
        return "";
    }
  }

  public Observable<ResultPage> search(String query, int page, String sort) {
    if (query.isEmpty()) {
      // rather than crashing, just log an error and return no results
      Timber.e(new RuntimeException(), "Search was called with an empty query");
      return Observable.just(ResultPage.create(new ArrayList<>(0), 0, page, true));
    }
    return api.search(query, page, Constants.PAGE_SIZE, sort)
              // retry on network failure 3 times
              .retry(3)
              // map the network response to the domain model
              .map(apiResponse -> {
                FeedResponse.Response response = apiResponse.response;

                List<FeedItem> results = new ArrayList<>();
                for (FeedResponse.Response.Doc doc : response.docs) {
                  FeedItem feedItem =
                      FeedItem.builder()
                              .id(doc.identifier)
                              // archive.org data is full of nulls, protect against it where possible
                              .title(NullUtil.defaultValue(doc.title))
                              .description(NullUtil.defaultValue(doc.description))
                              .publishedDate(ArchiveOrgUtil.parseDateApiV1(doc.publicdate))
                              .mediaType(ArchiveOrgUtil.parseMediaType(doc.mediatype,
                                                                       doc.type))
                              .build();
                  results.add(feedItem);
                }

                boolean isLastPage = (response.numFound - response.start) <= Constants.PAGE_SIZE;

                return ResultPage.create(results, response.numFound, page, isLastPage);
              });
  }
}