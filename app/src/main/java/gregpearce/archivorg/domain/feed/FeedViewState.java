package gregpearce.archivorg.domain.feed;

import com.google.auto.value.AutoValue;
import gregpearce.archivorg.domain.model.FeedItem;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
public abstract class FeedViewState {

  public abstract boolean refreshing();

  public abstract List<FeedItem> feedItems();

  public abstract boolean showBottomLoading();

  public abstract boolean showError();

  public static ImmutableFeedViewState.Builder from(FeedViewState feedViewState) {
    return ImmutableFeedViewState.builder().from(feedViewState);
  }
}