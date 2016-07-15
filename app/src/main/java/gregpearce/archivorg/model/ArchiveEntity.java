package gregpearce.archivorg.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ArchiveEntity {
  public static ArchiveEntity create(String title, String description) {
    return new AutoValue_ArchiveEntity(title, description);
  }

  public abstract String title();

  public abstract String description();
}
