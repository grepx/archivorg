package gregpearce.archivorg.ui.feed;

import com.google.auto.value.AutoValue;

import gregpearce.archivorg.ui.AutoValue_FeedItemViewModel;

@AutoValue
public abstract class FeedItemViewModel {
  public static FeedItemViewModel create(String title, String description) {
    return new AutoValue_FeedItemViewModel(title, description);
  }

  public static FeedItemViewModel defaultValue() {
    return create("", "");
  }

  public abstract String title();

  public abstract String description();
}

