package gregpearce.archivorg.network;

import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.network.FeedService;
import javax.inject.Inject;
import rx.Observable;

public class SearchFeedService implements FeedService {
  @Inject ArchiveOrgFeedService archiveOrgFeedService;

  private String filter = "";
  private String query = "";

  @Inject SearchFeedService() {
  }

  public void configure(FeedType feedType, String query) {
    filter = ArchiveOrgFeedService.getFeedTypeClause(feedType);
    if (query != null && !query.isEmpty()) {
      this.query = query;
    }
  }

  @Override public Observable<ResultPage> getPage(int page) {
    return archiveOrgFeedService.search(query + filter, page,
                                        ArchiveOrgFeedService.REVIEW_DATE_DESC);
  }
}