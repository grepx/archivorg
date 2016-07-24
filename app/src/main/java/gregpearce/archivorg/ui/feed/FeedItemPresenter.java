package gregpearce.archivorg.ui.feed;

import javax.inject.Inject;

import gregpearce.archivorg.ui.navigation.Navigator;

public class FeedItemPresenter {

  @Inject Navigator navigator;

  private String id;

  @Inject public FeedItemPresenter() {
  }

  public void start(String id) {
    this.id = id;
  }

  public void click() {
    navigator.navigateToDetail(id);
  }
}
