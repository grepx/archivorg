package gregpearce.archivorg.ui.feed;

import java.util.List;

public interface FeedView {
  void updateRefreshing(boolean isRefreshing);

  void updateFeed(List<FeedItem> feedItems, boolean reachedEndOfFeed);

  void showError(String error);
}
