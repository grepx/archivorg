package gregpearce.archivorg.ui;

import com.google.auto.value.AutoValue;

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

