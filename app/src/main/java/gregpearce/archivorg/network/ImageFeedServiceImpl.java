package gregpearce.archivorg.network;

import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.network.ImageFeedService;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

@Singleton public class ImageFeedServiceImpl implements ImageFeedService {

  private ArchiveOrgFeedService archiveOrgFeedService;

  @Inject ImageFeedServiceImpl(ArchiveOrgFeedService archiveOrgFeedService) {
    this.archiveOrgFeedService = archiveOrgFeedService;
  }

  private static final String videoFilter = " AND mediatype:(image)";

  @Override public Observable<ResultPage> search(String query, int page) {
    return archiveOrgFeedService.search(query + videoFilter, page,
                                        ArchiveOrgFeedService.DOWNLOADS_DESC);
  }

  @Override public Observable<ResultPage> latest(int page) {
    return archiveOrgFeedService.search(ArchiveOrgFeedService.TOP_QUERY + videoFilter, page,
                                        ArchiveOrgFeedService.REVIEW_DATE_DESC);
  }
}