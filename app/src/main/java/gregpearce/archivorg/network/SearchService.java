package gregpearce.archivorg.network;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gregpearce.archivorg.Constants;
import gregpearce.archivorg.model.ArchiveEntity;
import gregpearce.archivorg.model.ResultPage;
import retrofit2.Retrofit;
import rx.Observable;

public class SearchService {

  private SearchApi searchApi;

  @Inject SearchService(Retrofit retrofit) {
    searchApi = retrofit.create(SearchApi.class);
  }

  public Observable<ResultPage> search(String query, int page) {
    // map the api model to the domain model
    return searchApi.search(query, page, Constants.PAGE_SIZE)
        .map(apiResponse -> {
          SearchResponse.Response response = apiResponse.response;

          List<ArchiveEntity> results = new ArrayList<>();
          for (SearchResponse.Response.Doc doc : response.docs) {
            ArchiveEntity archiveEntity = ArchiveEntity.create(doc.title, doc.description);
            results.add(archiveEntity);
          }

          boolean isLastPage = (response.numFound - response.start) <= Constants.PAGE_SIZE;

          return ResultPage.create(results, response.numFound, page, isLastPage);
        });
  }
}
