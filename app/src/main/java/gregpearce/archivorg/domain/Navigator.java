package gregpearce.archivorg.domain;

import com.bluelinelabs.conductor.Router;
import gregpearce.archivorg.ui.feed.FeedType;

public interface Navigator {
  void navigateToDiscover();

  void navigateToDetail(String itemId);

  void navigateToSearch(FeedType feedType, String query);
}
