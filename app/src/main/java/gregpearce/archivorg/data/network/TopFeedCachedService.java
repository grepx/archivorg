package gregpearce.archivorg.data.network;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.service.FeedService;
import rx.Observable;

@AutoFactory(implementing = TopFeedServiceFactory.class)
public class TopFeedCachedService implements FeedService {
  private final TopFeedNetworkService topFeedNetworkService;
  // just cache page 1 since its the most commonly requested, adjust cache policy as required
  private Observable<ResultPage> page1;

  TopFeedCachedService(@Provided TopFeedNetworkServiceFactory topFeedNetworkServiceFactory,
                       FeedContentType feedContentType) {
    topFeedNetworkService = topFeedNetworkServiceFactory.get(feedContentType);
  }

  @Override public Observable<ResultPage> getPage(int page) {
    if (page == 1) {
      if (page1 == null) {
        page1 = topFeedNetworkService.getPage(page).cache();
      }
      return page1;
    } else {
      return topFeedNetworkService.getPage(page);
    }
  }
}