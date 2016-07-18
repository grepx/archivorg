package gregpearce.archivorg.ui.feed;

public interface FeedView {
  void setRefreshing(boolean isRefreshing);

  void showError(String error);
}
