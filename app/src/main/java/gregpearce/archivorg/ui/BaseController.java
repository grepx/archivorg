package gregpearce.archivorg.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import gregpearce.archivorg.di.ActivityComponent;

public abstract class BaseController extends ButterKnifeController {

  private boolean created = false;

  public BaseController() {
  }

  public BaseController(Bundle args) {
    super(args);
  }

  @NonNull @Override
  protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    if (!created) {
      created = true;
      onCreate();
    }
    return super.onCreateView(inflater, container);
  }

  @Override protected void onAttach(@NonNull View view) {
    super.onAttach(view);

    // Default settings to reset Activity UI state to each time a new controller is loaded.
    ActionBar actionBar = getActionBar();
    actionBar.setTitle("");
    actionBar.setDisplayHomeAsUpEnabled(false);
    actionBar.setHomeAsUpIndicator(0);

    DrawerLayout drawerLayout = getDrawerLayout();
    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        // Set the default behavior to be back navigation.
        getRouter().handleBack();
        return true;
    }
    return false;
  }

  protected ActionBar getActionBar() {
    ActionBarProvider actionBarProvider = (ActionBarProvider) getActivity();
    return actionBarProvider != null ? actionBarProvider.getSupportActionBar() : null;
  }

  protected DrawerLayout getDrawerLayout() {
    DrawerLayoutProvider drawerLayoutProvider = (DrawerLayoutProvider) getActivity();
    return drawerLayoutProvider != null ? drawerLayoutProvider.getDrawerLayout() : null;
  }

  protected ActivityComponent getComponent() {
    ActivityComponentProvider activityComponentProvider = (ActivityComponentProvider) getActivity();
    return activityComponentProvider != null ? activityComponentProvider.getActivityComponent() : null;
  }

  protected void onCreate() {
  }
}