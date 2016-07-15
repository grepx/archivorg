package gregpearce.archivorg.model;

public class ArchiveEntity {
  private final String title;
  private final String description;

  public ArchiveEntity(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }
}
