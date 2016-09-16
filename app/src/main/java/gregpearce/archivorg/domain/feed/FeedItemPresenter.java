package gregpearce.archivorg.domain.feed;

import gregpearce.archivorg.domain.BasePresenter;
import gregpearce.archivorg.domain.BaseView;
import gregpearce.archivorg.domain.Navigator;
import javax.inject.Inject;

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
