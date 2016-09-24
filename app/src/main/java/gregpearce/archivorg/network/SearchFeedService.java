package gregpearce.archivorg.network;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.network.FeedService;
import javax.inject.Inject;
import rx.Observable;

@AutoFactory
public class SearchFeedService implements FeedService {
  private ArchiveOrgFeedService archiveOrgFeedService;
  private String filter = "";
  private String query = "";

  SearchFeedService(@Provided ArchiveOrgFeedService archiveOrgFeedService, FeedType feedType,
                    String query) {
    this.archiveOrgFeedService = archiveOrgFeedService;
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