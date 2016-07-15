package gregpearce.archivorg.network;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gregpearce.archivorg.model.ArchiveEntity;
import gregpearce.archivorg.model.PagedResult;
import retrofit2.Retrofit;
import rx.Observable;

public class SearchService {

  private SearchApi searchApi;

  @Inject SearchService(Retrofit retrofit) {
    searchApi = retrofit.create(SearchApi.class);
  }

  public Observable<PagedResult> search(String query, int page) {
    // map the api model to the domain model
    return searchApi.search(query, page)
        .map(response -> {
          List<ArchiveEntity> results = new ArrayList<>();
          for (SearchResponse.Response.Doc doc : response.response.docs) {
            ArchiveEntity archiveEntity = ArchiveEntity.create(doc.title, doc.description);
            results.add(archiveEntity);
          }
          return PagedResult.create(results, response.response.numFound, page);
        });
  }
}
