package gregpearce.archivorg.ui.navigation;

import android.content.Intent;

import javax.inject.Inject;

import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.ui.BaseActivity;
import gregpearce.archivorg.ui.detail.DetailActivity;

@ActivityScope
public class Navigator {
  @Inject BaseActivity activity;

  @Inject public Navigator() {
  }

  public void navigateToMain() {
  }

  public void navigateToDetail(String itemId) {
    Intent callingIntent = DetailActivity.getCallingIntent(activity, itemId);
    activity.startActivity(callingIntent);
  }
}
