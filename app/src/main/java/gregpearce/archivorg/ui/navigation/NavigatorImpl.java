package gregpearce.archivorg.ui.navigation;

import android.app.Activity;
import android.content.Intent;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.ui.activity.MainActivity;
import gregpearce.archivorg.ui.detail.DetailController;
import javax.inject.Inject;

public class NavigatorImpl implements Navigator {
  @Inject Router router;

  @Inject public NavigatorImpl() {
  }

  public void navigateToDiscover() {
  }

  public void navigateToDetail(String itemId) {
    router.pushController(RouterTransaction.with(new DetailController(itemId)));
  }
}
