package gregpearce.archivorg.domain.feed;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import gregpearce.archivorg.domain.Navigator;

@AutoFactory
public class FeedItemPresenter {

  private Navigator navigator;
  private String id;

  public FeedItemPresenter(String id, @Provided Navigator navigator) {
    this.id = id;
    this.navigator = navigator;
  }

  public void click() {
    navigator.navigateToDetailBottomSheet(id);
  }
}
