package gregpearce.archivorg.domain.model;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import java.util.List;
import org.threeten.bp.Instant;

@AutoValue
public abstract class ArchiveFile {

  @Nullable public abstract String name();

  @Nullable public abstract String size();

  @Nullable public abstract String length();

  @Nullable public abstract String source();

  @Nullable public abstract String format();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract ArchiveFile.Builder name(@Nullable String name);

    public abstract ArchiveFile.Builder size(@Nullable String size);

    public abstract ArchiveFile.Builder length(@Nullable String length);

    public abstract ArchiveFile.Builder source(@Nullable String source);

    public abstract ArchiveFile.Builder format(@Nullable String format);

    public abstract ArchiveFile build();
  }

  public abstract ArchiveFile.Builder toBuilder();

  public static ArchiveFile.Builder builder() {
    return new AutoValue_ArchiveFile.Builder();
  }
}