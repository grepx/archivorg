package gregpearce.archivorg.domain;

import gregpearce.archivorg.domain.model.FeedContentType;

public interface Navigator {
  void navigateToDetailBottomSheet(String itemId);

  void navigateToSearch(FeedContentType feedContentType, String query);
}
