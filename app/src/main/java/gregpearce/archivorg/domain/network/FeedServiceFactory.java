package gregpearce.archivorg.domain.network;

import gregpearce.archivorg.domain.model.FeedContentType;

public interface FeedServiceFactory {
  FeedService getTopFeed(FeedContentType feedContentType);

  FeedService getSearchFeed(FeedContentType feedContentType, String query);
}
