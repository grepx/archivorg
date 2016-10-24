package gregpearce.archivorg.domain.service;

import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.FeedType;

public interface FeedServiceFactory {
  FeedService get(FeedType feedType, FeedContentType feedContentType, String query);
}
