package gregpearce.archivorg.domain.model;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue public abstract class ArchiveFile {
  public static ArchiveFile create(@Nullable String name, @Nullable String size,
                                   @Nullable String length, @Nullable String source,
                                   @Nullable String format) {
    return new AutoValue_ArchiveFile(name, size, length, source, format);
  }

  @Nullable public abstract String name();

  @Nullable public abstract String size();

  @Nullable public abstract String length();

  @Nullable public abstract String source();

  @Nullable public abstract String format();
}