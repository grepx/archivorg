package gregpearce.archivorg.data.network;

import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.service.FeedService;

public interface TopFeedServiceFactory {
  FeedService get(FeedContentType feedContentType);
}
