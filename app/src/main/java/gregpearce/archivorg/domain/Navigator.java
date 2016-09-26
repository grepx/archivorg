package gregpearce.archivorg.domain;

import gregpearce.archivorg.domain.model.FeedType;

public interface Navigator {
  void navigateToDiscover();

  void navigateToDetail(String itemId);

  void navigateToDetailBottomSheet(String itemId);

  void navigateToSearch(FeedType feedType, String query);
}
