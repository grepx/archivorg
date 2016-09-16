package gregpearce.archivorg.domain.model;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import org.threeten.bp.Instant;

@AutoValue public abstract class FeedItem {
  public static FeedItem create(String id, String title, String description, Instant publishedDate,
      MediaType mediaType) {
    return new AutoValue_FeedItem(id, title, description, publishedDate, mediaType);
  }

  public abstract String id();

  public abstract String title();

  public abstract String description();

  @Nullable public abstract Instant publishedDate();

  public abstract MediaType mediaType();
}
