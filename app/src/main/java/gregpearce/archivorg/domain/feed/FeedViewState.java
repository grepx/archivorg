package gregpearce.archivorg.domain.feed;

import com.google.auto.value.AutoValue;
import gregpearce.archivorg.domain.model.FeedItem;
import java.util.List;

@AutoValue
public abstract class FeedViewState {

  // Properties

  public abstract boolean showRefreshing();

  public abstract List<FeedItem> feedItems();

  public abstract boolean showEmptyFeedMessage();

  public abstract boolean showBottomLoading();

  public abstract boolean showError();

  // Builder

  @AutoValue.Builder
  abstract static class Builder {

    public abstract Builder showRefreshing(boolean refreshing);

    public abstract Builder feedItems(List<FeedItem> feedItems);

    public abstract Builder showEmptyFeedMessage(boolean showEmptyFeedMessage);

    public abstract Builder showBottomLoading(boolean showBottomLoading);

    public abstract Builder showError(boolean showError);

    abstract FeedViewState build();
  }

  public abstract Builder toBuilder();

  static Builder builder() {
    return new AutoValue_FeedViewState.Builder();
  }
}