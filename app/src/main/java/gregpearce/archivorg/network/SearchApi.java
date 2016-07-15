package gregpearce.archivorg.network;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SearchApi {
  @GET("advancedsearch.php") Observable<SearchResponse> search(@Query("q") String query, @Query("page") int page, @Query("output") String output);
}
