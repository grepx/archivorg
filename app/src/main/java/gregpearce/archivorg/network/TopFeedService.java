package gregpearce.archivorg.network;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.network.FeedService;
import javax.inject.Inject;
import rx.Observable;

@AutoFactory
public class TopFeedService implements FeedService {
  private ArchiveOrgFeedService archiveOrgFeedService;
  private String filter = "";

  TopFeedService(@Provided ArchiveOrgFeedService archiveOrgFeedService, FeedType feedType) {
    this.archiveOrgFeedService = archiveOrgFeedService;
    filter = ArchiveOrgFeedService.getFeedTypeClause(feedType);
  }

  @Override public Observable<ResultPage> getPage(int page) {
    return archiveOrgFeedService.search(ArchiveOrgFeedService.TOP_QUERY + filter, page,
                                        ArchiveOrgFeedService.REVIEW_DATE_DESC);
  }
}