package gregpearce.archivorg.model;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class ResultPage {
  public static ResultPage create(List<ArchiveFeedItem> results, int totalCount, int page, boolean isLastPage) {
    return new AutoValue_ResultPage(results, totalCount, page, isLastPage);
  }

  public abstract List<ArchiveFeedItem> results();

  public abstract int totalCount();

  public abstract int page();

  public abstract boolean isLastPage();
}