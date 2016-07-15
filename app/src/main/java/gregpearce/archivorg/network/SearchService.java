package gregpearce.archivorg.network;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gregpearce.archivorg.model.ArchiveEntity;
import gregpearce.archivorg.model.PagedResult;
import retrofit2.Retrofit;
import rx.Observable;
import rx.schedulers.Schedulers;

public class SearchService {

  private SearchApi searchApi;

  @Inject SearchService(Retrofit retrofit) {
    searchApi = retrofit.create(SearchApi.class);
  }

  public Observable<PagedResult> search(String query, int page) {
    // map the api model to the domain model
    return searchApi.search(query, page, "json")
        .subscribeOn(Schedulers.io())
        .map(response -> {
          List<ArchiveEntity> results = new ArrayList<>();
          for (SearchResponse.Response.Doc doc : response.response.docs) {
            ArchiveEntity archiveEntity = new ArchiveEntity(doc.title, doc.description);
            results.add(archiveEntity);
          }
          return new PagedResult(results, response.response.numFound, page);
        });
  }
}
