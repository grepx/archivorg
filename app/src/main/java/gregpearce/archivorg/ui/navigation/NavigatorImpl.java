package gregpearce.archivorg.ui.navigation;

import android.app.Activity;
import android.content.Intent;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.ui.detail.MainActivity;
import javax.inject.Inject;

public class NavigatorImpl implements Navigator {
  @Inject Activity activity;

  @Inject public NavigatorImpl() {
  }

  public void navigateToDiscover() {
  }

  public void navigateToDetail(String itemId) {
    Intent callingIntent = MainActivity.getCallingIntent(activity, itemId);
    activity.startActivity(callingIntent);
  }
}
