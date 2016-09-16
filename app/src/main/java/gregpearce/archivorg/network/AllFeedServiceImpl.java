package gregpearce.archivorg.network;

import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.network.AllFeedService;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

@Singleton public class AllFeedServiceImpl implements AllFeedService {

  private ArchiveOrgFeedService archiveOrgFeedService;

  @Inject AllFeedServiceImpl(ArchiveOrgFeedService archiveOrgFeedService) {
    this.archiveOrgFeedService = archiveOrgFeedService;
  }

  @Override public Observable<ResultPage> search(String query, int page) {
    return archiveOrgFeedService.search(query, page, ArchiveOrgFeedService.DOWNLOADS_DESC);
  }

  @Override public Observable<ResultPage> latest(int page) {
    return archiveOrgFeedService.search(ArchiveOrgFeedService.TOP_QUERY, page,
        ArchiveOrgFeedService.REVIEW_DATE_DESC);
  }
}