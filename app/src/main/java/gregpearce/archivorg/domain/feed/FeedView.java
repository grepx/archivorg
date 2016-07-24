package gregpearce.archivorg.domain.feed;

import java.util.List;

import gregpearce.archivorg.domain.BaseView;
import gregpearce.archivorg.domain.model.FeedItem;

public interface FeedView extends BaseView {
  void updateRefreshing(boolean isRefreshing);

  void updateFeed(List<FeedItem> feedItems, boolean reachedEndOfFeed);

  void showError();
}
