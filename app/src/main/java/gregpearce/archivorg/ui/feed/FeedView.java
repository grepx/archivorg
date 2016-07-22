package gregpearce.archivorg.ui.feed;

import java.util.List;

import gregpearce.archivorg.model.FeedItem;

public interface FeedView {
  void updateRefreshing(boolean isRefreshing);

  void updateFeed(List<FeedItem> feedItems, boolean reachedEndOfFeed);

  void showError();
}
