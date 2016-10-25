package gregpearce.archivorg.domain.search;

import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.domain.model.FeedContentType;
import javax.inject.Inject;

public class SearchPresenter {
  @Inject Navigator navigator;

  @Inject public SearchPresenter() {
  }

  public void search(String query) {
    navigator.navigateToSearch(FeedContentType.All, query);
  }

  public void searchOptionClicked(FeedContentType feedContentType, String query) {
    navigator.navigateToSearch(feedContentType, query);
  }
}
