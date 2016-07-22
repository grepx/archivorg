package gregpearce.archivorg.network;

import javax.inject.Inject;
import javax.inject.Singleton;

import gregpearce.archivorg.model.ResultPage;
import rx.Observable;

@Singleton
public class PopularSearchService implements SearchService {

  private ArchiveOrgV1Service archiveOrgV1Service;

  @Inject PopularSearchService(ArchiveOrgV1Service archiveOrgV1Service) {
    this.archiveOrgV1Service = archiveOrgV1Service;
  }

  @Override public Observable<ResultPage> search(String query, int page) {
    if (query.isEmpty()) {
      return archiveOrgV1Service.search(ArchiveOrgV1Service.TOP_QUERY, page, ArchiveOrgV1Service.REVIEW_DATE_DESC);
    } else {
      return archiveOrgV1Service.search(query, page, ArchiveOrgV1Service.DOWNLOADS_DESC);
    }
  }
}