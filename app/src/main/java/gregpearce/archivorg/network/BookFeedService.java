package gregpearce.archivorg.network;

import javax.inject.Inject;
import javax.inject.Singleton;

import gregpearce.archivorg.model.ResultPage;
import rx.Observable;

@Singleton
public class BookFeedService implements FeedService {

  private ArchiveOrgFeedService archiveOrgFeedService;

  @Inject BookFeedService(ArchiveOrgFeedService archiveOrgFeedService) {
    this.archiveOrgFeedService = archiveOrgFeedService;
  }

  @Override public Observable<ResultPage> search(String query, int page) {
    final String videoFilter = " AND mediatype:(texts)";
    if (query.isEmpty()) {
      return archiveOrgFeedService.search(ArchiveOrgFeedService.TOP_QUERY + videoFilter, page, ArchiveOrgFeedService.REVIEW_DATE_DESC);
    } else {
      return archiveOrgFeedService.search(query + videoFilter, page, ArchiveOrgFeedService.DOWNLOADS_DESC);
    }
  }
}