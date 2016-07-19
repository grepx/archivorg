package gregpearce.archivorg.ui.feed;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FeedItem {
  public static FeedItem create(String title, String description) {
    return new AutoValue_FeedItem(title, description);
  }

  public static FeedItem defaultValue() {
    return create("", "");
  }

  public abstract String title();

  public abstract String description();
}

