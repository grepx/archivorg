package gregpearce.archivorg.network;

import gregpearce.archivorg.domain.network.FeedService;
import gregpearce.archivorg.domain.network.FeedServiceFactory;
import gregpearce.archivorg.domain.model.FeedType;
import javax.inject.Inject;

public class FeedServiceFactoryImpl implements FeedServiceFactory {

  @Inject TopFeedServiceFactory topFeedServiceFactory;
  @Inject SearchFeedServiceFactory searchFeedServiceProvider;

  @Inject public FeedServiceFactoryImpl() {
  }

  @Override public FeedService getTopFeed(FeedType feedType) {
    TopFeedService feedService = topFeedServiceFactory.create(feedType);
    return feedService;
  }

  @Override public FeedService getSearchFeed(FeedType feedType, String query) {
    SearchFeedService feedService = searchFeedServiceProvider.create(feedType, query);
    return feedService;
  }
}
