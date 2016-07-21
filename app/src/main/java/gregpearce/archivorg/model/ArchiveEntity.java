package gregpearce.archivorg.model;

import com.google.auto.value.AutoValue;

import org.threeten.bp.LocalDateTime;

@AutoValue
public abstract class ArchiveEntity {
  public static ArchiveEntity create(String title, String description, LocalDateTime publishedDate, MediaType mediaType) {
    return new AutoValue_ArchiveEntity(title, description, publishedDate, mediaType);
  }

  public abstract String title();

  public abstract String description();

  public abstract LocalDateTime publishedDate();

  public abstract MediaType contentType();
}
