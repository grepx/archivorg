package gregpearce.archivorg.data.network;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.service.FeedService;
import rx.Observable;

@AutoFactory(implementing = TopFeedServiceFactory.class)
public class TopFeedNetworkService implements FeedService {
  private ArchiveOrgFeedService archiveOrgFeedService;
  private String filter = "";

  TopFeedNetworkService(@Provided ArchiveOrgFeedService archiveOrgFeedService, FeedContentType feedContentType) {
    this.archiveOrgFeedService = archiveOrgFeedService;
    filter = ArchiveOrgFeedService.getFeedTypeClause(feedContentType);
  }

  @Override public Observable<ResultPage> getPage(int page) {
    return archiveOrgFeedService.search(ArchiveOrgFeedService.TOP_QUERY + filter, page,
                                        ArchiveOrgFeedService.REVIEW_DATE_DESC);
  }
}