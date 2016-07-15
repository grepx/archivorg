package gregpearce.archivorg.model;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class PagedResult {
  public static PagedResult create(List<ArchiveEntity> results, int totalCount, int page) {
    return new AutoValue_PagedResult(results, totalCount, page);
  }

  public abstract List<ArchiveEntity> results();

  public abstract int totalCount();

  public abstract int page();
}