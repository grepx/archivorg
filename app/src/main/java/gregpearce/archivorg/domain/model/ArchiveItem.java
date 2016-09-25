package gregpearce.archivorg.domain.model;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import java.util.List;
import org.threeten.bp.Instant;

@AutoValue
public abstract class ArchiveItem {
  public static ArchiveItem create(String title, String description, Instant publishedDate,
                                   MediaType mediaType, String creator, String uploader,
                                   List<ArchiveFile> files) {
    return new AutoValue_ArchiveItem(title, description, publishedDate, mediaType, creator,
                                     uploader, files);
  }

  public abstract String title();

  public abstract String description();

  @Nullable public abstract Instant publishedDate();

  public abstract MediaType mediaType();

  public abstract String creator();

  public abstract String uploader();

  public abstract List<ArchiveFile> files();
}
