package gregpearce.archivorg.network;

import gregpearce.archivorg.model.ResultPage;
import rx.Observable;

public interface SearchService {
  Observable<ResultPage> search(String query, int page);
}