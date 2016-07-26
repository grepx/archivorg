package gregpearce.archivorg.domain.feed;

import javax.inject.Inject;

import gregpearce.archivorg.domain.BasePresenter;
import gregpearce.archivorg.domain.BaseView;
import gregpearce.archivorg.domain.Navigator;

public class FeedItemPresenter extends BasePresenter<BaseView> {

  @Inject Navigator navigator;

  private String id;

  @Inject public FeedItemPresenter() {
  }

  public void init(String id) {
    this.id = id;
  }

  public void click() {
    navigator.navigateToDetail(id);
  }

  @Override protected void syncView(BaseView view) {
  }
}
