package gregpearce.archivorg.network;

import javax.inject.Inject;
import javax.inject.Singleton;

import gregpearce.archivorg.model.ResultPage;
import rx.Observable;

@Singleton
public class PopularSearchService implements SearchService {

  private ArchiveOrgService archiveOrgService;

  @Inject PopularSearchService(ArchiveOrgService archiveOrgService) {
    this.archiveOrgService = archiveOrgService;
  }

  @Override public Observable<ResultPage> search(String query, int page) {
    if (query.isEmpty()) {
      return archiveOrgService.search(ArchiveOrgService.TOP_QUERY, page, ArchiveOrgService.REVIEW_DATE_DESC);
    } else {
      return archiveOrgService.search(query, page, ArchiveOrgService.DOWNLOADS_DESC);
    }
  }
}