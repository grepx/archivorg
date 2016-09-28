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
import butterknife.ButterKnife;
import gregpearce.archivorg.MainApplication;
import gregpearce.archivorg.di.ControllerComponent;
import gregpearce.archivorg.di.ControllerModule;
import gregpearce.archivorg.di.DaggerControllerComponent;
import gregpearce.archivorg.ui.activity.DrawerLayoutProvider;

public abstract class BaseController extends ButterKnifeController {

  private boolean created = false;
  private ControllerComponent controllerComponent;

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

  protected void onCreate() {
  }

  @Override public void onDestroy() {
    super.onDestroy();
    MainApplication.getRefWatcher().watch(this);
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

  public ControllerComponent getComponent() {
    if (controllerComponent == null) {
      if (getParentController() != null) {
        // this is a child controller - use the parent's component
        // perhaps in the future child controller scope will be added
        controllerComponent = ((BaseController) getParentController()).getComponent();
      } else {
        controllerComponent = DaggerControllerComponent.builder()
                                                       .applicationComponent(
                                                           MainApplication.APP_COMPONENT)
                                                       .controllerModule(new ControllerModule(this))
                                                       .build();
      }
    }
    return controllerComponent;
  }

  public void finish() {
    getRouter().popController(this);
  }
}