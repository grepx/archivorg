package gregpearce.archivorg.domain.network;

import gregpearce.archivorg.domain.model.ResultPage;
import rx.Observable;

public interface FeedService {
  Observable<ResultPage> search(String query, int page);

  Observable<ResultPage> latest(int page);
}