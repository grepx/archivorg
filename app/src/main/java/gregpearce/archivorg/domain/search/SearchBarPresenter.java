package gregpearce.archivorg.domain.search;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.FeedType;

@AutoFactory
public class SearchBarPresenter {
  private FeedType feedType;
  Navigator navigator;

  public SearchBarPresenter(FeedType feedType,
                            @Provided Navigator navigator) {
    this.feedType = feedType;
    this.navigator = navigator;
  }

  public void search(String query) {
    navigator.navigateToSearch(feedType, FeedContentType.All, query);
  }

  public void searchOptionClicked(FeedContentType feedContentType, String query) {
    navigator.navigateToSearch(feedType, feedContentType, query);
  }
}
