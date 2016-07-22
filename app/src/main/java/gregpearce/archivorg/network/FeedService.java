package gregpearce.archivorg.network;

import gregpearce.archivorg.model.ResultPage;
import rx.Observable;

public interface FeedService {
  Observable<ResultPage> search(String query, int page);

  Observable<ResultPage> latest(int page);
}