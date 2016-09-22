package gregpearce.archivorg.domain.network;

import gregpearce.archivorg.domain.model.ResultPage;
import rx.Observable;

public interface FeedService {
  Observable<ResultPage> getPage(int page);
}