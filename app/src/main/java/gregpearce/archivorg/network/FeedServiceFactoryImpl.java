package gregpearce.archivorg.network;

import gregpearce.archivorg.domain.network.FeedService;
import gregpearce.archivorg.domain.network.FeedServiceFactory;
import gregpearce.archivorg.domain.model.FeedType;
import javax.inject.Inject;
import javax.inject.Provider;

public class FeedServiceFactoryImpl implements FeedServiceFactory {

  @Inject Provider<TopFeedService> topFeedServiceProvider;
  @Inject Provider<SearchFeedService> searchFeedServiceProvider;

  @Inject public FeedServiceFactoryImpl() {
  }

  @Override public FeedService getTopFeed(FeedType feedType) {
    TopFeedService feedService = topFeedServiceProvider.get();
    feedService.configure(feedType);
    return feedService;
  }

  @Override public FeedService getSearchFeed(FeedType feedType, String query) {
    SearchFeedService feedService = searchFeedServiceProvider.get();
    feedService.configure(feedType, query);
    return feedService;
  }
}
