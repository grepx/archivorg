package gregpearce.archivorg.domain.model;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import java.util.List;
import org.threeten.bp.Instant;

@AutoValue
public abstract class ArchiveItem {

  public abstract String title();

  public abstract String description();

  public abstract @Nullable Instant publishedDate();

  public abstract MediaType mediaType();

  public abstract String creator();

  public abstract String uploader();

  public abstract List<ArchiveFile> files();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract ArchiveItem.Builder title(String title);

    public abstract ArchiveItem.Builder description(String description);

    public abstract ArchiveItem.Builder publishedDate(Instant publishedDate);

    public abstract ArchiveItem.Builder mediaType(MediaType mediaType);

    public abstract ArchiveItem.Builder creator(String creator);

    public abstract ArchiveItem.Builder uploader(String uploader);

    public abstract ArchiveItem.Builder files(List<ArchiveFile> files);

    public abstract ArchiveItem build();
  }

  public abstract ArchiveItem.Builder toBuilder();

  public static ArchiveItem.Builder builder() {
    return new AutoValue_ArchiveItem.Builder();
  }
}
