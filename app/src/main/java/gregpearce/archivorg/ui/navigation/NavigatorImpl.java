package gregpearce.archivorg.ui.navigation;

import android.content.Intent;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.ui.BaseActivity;
import gregpearce.archivorg.ui.detail.DetailActivity;
import javax.inject.Inject;

public class NavigatorImpl implements Navigator {
  @Inject BaseActivity activity;

  @Inject public NavigatorImpl() {
  }

  public void navigateToDiscover() {
  }

  public void navigateToDetail(String itemId) {
    Intent callingIntent = DetailActivity.getCallingIntent(activity, itemId);
    activity.startActivity(callingIntent);
  }
}
