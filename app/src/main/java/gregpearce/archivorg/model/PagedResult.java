package gregpearce.archivorg.model;

import java.util.List;

public class PagedResult {
  private final List<ArchiveEntity> results;
  private final int totalCount;
  private final int page;

  public PagedResult(List<ArchiveEntity> results, int totalCount, int page) {
    this.results = results;
    this.totalCount = totalCount;
    this.page = page;
  }

  public List<ArchiveEntity> getResults() {
    return results;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public int getPage() {
    return page;
  }
}
