package gregpearce.archivorg.network;

import javax.inject.Inject;
import javax.inject.Singleton;

import gregpearce.archivorg.model.ResultPage;
import rx.Observable;

@Singleton
public class VideoFeedService implements FeedService {

  private ArchiveOrgFeedService archiveOrgFeedService;

  @Inject VideoFeedService(ArchiveOrgFeedService archiveOrgFeedService) {
    this.archiveOrgFeedService = archiveOrgFeedService;
  }

  private static final String videoFilter = " AND mediatype:(movies)";

  @Override public Observable<ResultPage> search(String query, int page) {
    return archiveOrgFeedService.search(query + videoFilter, page, ArchiveOrgFeedService.DOWNLOADS_DESC);
  }

  @Override public Observable<ResultPage> latest(int page) {
    return archiveOrgFeedService.search(ArchiveOrgFeedService.TOP_QUERY + videoFilter, page, ArchiveOrgFeedService.REVIEW_DATE_DESC);
  }
}