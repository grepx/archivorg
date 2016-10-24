package gregpearce.archivorg.network;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.service.FeedService;
import rx.Observable;

@AutoFactory
public class SearchFeedService implements FeedService {
  private ArchiveOrgFeedService archiveOrgFeedService;
  private String filter = "";
  private String query = "";

  SearchFeedService(@Provided ArchiveOrgFeedService archiveOrgFeedService, FeedContentType feedContentType,
                    String query) {
    this.archiveOrgFeedService = archiveOrgFeedService;
    filter = ArchiveOrgFeedService.getFeedTypeClause(feedContentType);
    if (query != null && !query.isEmpty()) {
      this.query = query;
    }
  }

  @Override public Observable<ResultPage> getPage(int page) {
    return archiveOrgFeedService.search(query + filter, page,
                                        ArchiveOrgFeedService.REVIEW_DATE_DESC);
  }
}