package gregpearce.archivorg.domain.model;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import org.threeten.bp.Instant;

@AutoValue
public abstract class FeedItem {
  public abstract String id();

  public abstract String title();

  public abstract String description();

  public abstract @Nullable Instant publishedDate();

  public abstract MediaType mediaType();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract FeedItem.Builder id(String id);

    public abstract FeedItem.Builder title(String title);

    public abstract FeedItem.Builder description(String description);

    public abstract FeedItem.Builder publishedDate(@Nullable Instant publishedDate);

    public abstract FeedItem.Builder mediaType(MediaType mediaType);

    public abstract FeedItem build();
  }

  public abstract FeedItem.Builder toBuilder();

  public static FeedItem.Builder builder() {
    return new AutoValue_FeedItem.Builder();
  }
}
