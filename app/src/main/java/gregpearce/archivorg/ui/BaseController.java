package gregpearce.archivorg.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import gregpearce.archivorg.di.ControllerComponent;
import gregpearce.archivorg.ui.activity.ControllerComponentProvider;
import gregpearce.archivorg.ui.activity.DrawerLayoutProvider;

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
    AppCompatActivity activity = (AppCompatActivity) getActivity();
    return activity != null ? activity.getSupportActionBar() : null;
  }

  protected void setActionBar(Toolbar actionBar) {
    AppCompatActivity activity = (AppCompatActivity) getActivity();
    activity.setSupportActionBar(actionBar);
  }

  protected DrawerLayout getDrawerLayout() {
    DrawerLayoutProvider drawerLayoutProvider = (DrawerLayoutProvider) getActivity();
    return drawerLayoutProvider != null ? drawerLayoutProvider.getDrawerLayout() : null;
  }

  protected ControllerComponent getComponent() {
    ControllerComponentProvider
        controllerComponentProvider = (ControllerComponentProvider) getActivity();
    return controllerComponentProvider != null ? controllerComponentProvider.getControllerComponent() : null;
  }

  protected void onCreate() {
  }
}