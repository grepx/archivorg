package gregpearce.archivorg.ui.feed;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FeedItemModel {
  public static FeedItemModel create(String title, String description) {
    return new AutoValue_FeedItemModel(title, description);
  }

  public static FeedItemModel defaultValue() {
    return create("", "");
  }

  public abstract String title();

  public abstract String description();
}

