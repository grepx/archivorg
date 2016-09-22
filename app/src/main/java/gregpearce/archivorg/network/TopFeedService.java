package gregpearce.archivorg.network;

import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.network.FeedService;
import javax.inject.Inject;
import rx.Observable;

public class TopFeedService implements FeedService {

  @Inject ArchiveOrgFeedService archiveOrgFeedService;

  private String filter = "";

  @Inject TopFeedService() {
  }

  public void configure(FeedType feedType) {
    filter = ArchiveOrgFeedService.getFeedTypeClause(feedType);
  }

  @Override public Observable<ResultPage> getPage(int page) {
    return archiveOrgFeedService.search(ArchiveOrgFeedService.TOP_QUERY + filter, page,
                                        ArchiveOrgFeedService.REVIEW_DATE_DESC);
  }
}