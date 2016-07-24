package gregpearce.archivorg.ui.navigation;

import android.content.Intent;

import javax.inject.Inject;

import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.ui.BaseActivity;
import gregpearce.archivorg.ui.detail.DetailActivity;

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
