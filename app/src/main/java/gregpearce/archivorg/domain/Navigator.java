package gregpearce.archivorg.domain;

import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.FeedType;

public interface Navigator {
  void navigateToDetail(String itemId);

  void navigateToSearch(FeedType feedType, FeedContentType feedContentType, String query);

  void navigateToFiles(String archiveItemId);
}
