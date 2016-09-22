package gregpearce.archivorg.domain.network;

import gregpearce.archivorg.domain.model.FeedType;

public interface FeedServiceFactory {
  FeedService getTopFeed(FeedType feedType);

  FeedService getSearchFeed(FeedType feedType, String query);
}
