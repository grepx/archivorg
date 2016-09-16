package gregpearce.archivorg.domain.model;

import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue public abstract class ResultPage {
  public static ResultPage create(List<FeedItem> results, int totalCount, int page,
      boolean isLastPage) {
    return new AutoValue_ResultPage(results, totalCount, page, isLastPage);
  }

  public abstract List<FeedItem> results();

  public abstract int totalCount();

  public abstract int page();

  public abstract boolean isLastPage();
}