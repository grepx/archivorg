package gregpearce.archivorg.domain.model;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import java.util.List;
import org.threeten.bp.Instant;

@AutoValue
public abstract class ArchiveItem {

  public abstract String id();

  public abstract String title();

  public abstract String description();

  public abstract @Nullable Instant publishedDate();

  public abstract MediaType mediaType();

  public abstract String creator();

  public abstract String uploader();

  public abstract List<ArchiveFile> files();

  // The date that this was bookmarked (null if not currently bookmarked)
  public abstract @Nullable Instant bookmarkedDate();

  // The date of last file downloaded (null if no files are currently downloaded)
  public abstract @Nullable Instant downloadedDate();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract ArchiveItem.Builder id(String id);

    public abstract ArchiveItem.Builder title(String title);

    public abstract ArchiveItem.Builder description(String description);

    public abstract ArchiveItem.Builder publishedDate(Instant publishedDate);

    public abstract ArchiveItem.Builder mediaType(MediaType mediaType);

    public abstract ArchiveItem.Builder creator(String creator);

    public abstract ArchiveItem.Builder uploader(String uploader);

    public abstract ArchiveItem.Builder files(List<ArchiveFile> files);

    public abstract ArchiveItem.Builder bookmarkedDate(Instant bookmarkedDate);

    public abstract ArchiveItem.Builder downloadedDate(Instant downloadedDate);

    public abstract ArchiveItem build();
  }

  public abstract ArchiveItem.Builder toBuilder();

  public static ArchiveItem.Builder builder() {
    return new AutoValue_ArchiveItem.Builder();
  }
}
