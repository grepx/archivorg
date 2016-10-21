package gregpearce.archivorg.network;

import gregpearce.archivorg.domain.network.FeedService;
import gregpearce.archivorg.domain.network.FeedServiceFactory;
import gregpearce.archivorg.domain.model.FeedContentType;
import javax.inject.Inject;

public class FeedServiceFactoryImpl implements FeedServiceFactory {

  @Inject TopFeedServiceFactory topFeedServiceFactory;
  @Inject SearchFeedServiceFactory searchFeedServiceProvider;

  @Inject public FeedServiceFactoryImpl() {
  }

  @Override public FeedService getTopFeed(FeedContentType feedContentType) {
    TopFeedService feedService = topFeedServiceFactory.create(feedContentType);
    return feedService;
  }

  @Override public FeedService getSearchFeed(FeedContentType feedContentType, String query) {
    SearchFeedService feedService = searchFeedServiceProvider.create(feedContentType, query);
    return feedService;
  }
}
