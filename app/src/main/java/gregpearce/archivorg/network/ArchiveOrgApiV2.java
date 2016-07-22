package gregpearce.archivorg.network;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ArchiveOrgApiV2 {
  @GET("items/{id}")
  Observable<ItemResponse> items(@Path("id") String id);
}
