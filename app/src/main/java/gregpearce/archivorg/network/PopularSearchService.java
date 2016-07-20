package gregpearce.archivorg.network;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gregpearce.archivorg.Constants;
import gregpearce.archivorg.model.ArchiveEntity;
import gregpearce.archivorg.model.ResultPage;
import gregpearce.archivorg.util.NullUtil;
import retrofit2.Retrofit;
import rx.Observable;

public class PopularSearchService implements SearchService {

  private SearchApi searchApi;

  @Inject PopularSearchService(Retrofit retrofit) {
    searchApi = retrofit.create(SearchApi.class);
  }

  @Override public Observable<ResultPage> search(String query, int page) {
    // map the api model to the domain model
    return searchApi.search(query, page, Constants.PAGE_SIZE)
        .map(apiResponse -> {
          SearchResponse.Response response = apiResponse.response;

          List<ArchiveEntity> results = new ArrayList<>();
          for (SearchResponse.Response.Doc doc : response.docs) {
            ArchiveEntity archiveEntity = ArchiveEntity.create(
                // archive.org is full of nulls, protect against it
                NullUtil.defaultNullValue(doc.title),
                NullUtil.defaultNullValue(doc.description)
            );
            results.add(archiveEntity);
          }

          boolean isLastPage = (response.numFound - response.start) <= Constants.PAGE_SIZE;

          return ResultPage.create(results, response.numFound, page, isLastPage);
        });
  }
}
