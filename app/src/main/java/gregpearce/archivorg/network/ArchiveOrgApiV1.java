package gregpearce.archivorg.network;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ArchiveOrgApiV1 {
  @GET("advancedsearch.php")
  Observable<FeedResponse> search(@Query("q") String query, @Query("page") int page, @Query("rows") int rows,
                                  @Query("sort[]") String sort);
}
