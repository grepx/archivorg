package gregpearce.archivorg.domain;

import gregpearce.archivorg.domain.model.FeedContentType;

public interface Navigator {
  void navigateToDetail(String itemId);

  void navigateToSearch(FeedContentType feedContentType, String query);
}
