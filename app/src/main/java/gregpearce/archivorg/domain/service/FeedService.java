package gregpearce.archivorg.domain.service;

import gregpearce.archivorg.domain.model.ResultPage;
import rx.Observable;

public interface FeedService {
  Observable<ResultPage> getPage(int page);
}