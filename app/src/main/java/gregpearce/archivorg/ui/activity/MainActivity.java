package gregpearce.archivorg.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import butterknife.BindView;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import gregpearce.archivorg.MainApplication;
import gregpearce.archivorg.R;
import gregpearce.archivorg.di.ControllerComponent;
import gregpearce.archivorg.di.ControllerModule;
import gregpearce.archivorg.di.DaggerControllerComponent;
import gregpearce.archivorg.ui.discover.DiscoverController;

public class MainActivity extends AppCompatActivity
    implements ActionBarProvider, DrawerLayoutProvider, ControllerComponentProvider {

  @BindView(R.id.controller_container) ViewGroup container;
  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

  private Router router;

  private ControllerComponent controllerComponent;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    // Set up the controller container.
    container = (ViewGroup) findViewById(R.id.controller_container);

    // Set up the navigation drawer.
    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    if (navigationView != null) {
      setupDrawerContent(navigationView);
    }

    router = Conductor.attachRouter(this, container, savedInstanceState);
    if (!router.hasRootController()) {
      router.setRoot(RouterTransaction.with(new DiscoverController()));
    }
  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(menuItem -> {
      switch (menuItem.getItemId()) {
        case R.id.drawer_item1:
          //router.setRoot(RouterTransaction.with(new TasksController()));
          break;
        case R.id.drawer_item2:
          //router.setRoot(RouterTransaction.with(new StatisticsController()));
          break;
        default:
          break;
      }
      // Close the navigation drawer when an item is selected.
      menuItem.setChecked(true);
      drawerLayout.closeDrawers();
      return true;
    });
  }

  public ControllerComponent getControllerComponent() {
    if (controllerComponent == null) {
      controllerComponent = DaggerControllerComponent.builder()
          .applicationComponent(MainApplication.APP_COMPONENT)
          .controllerModule(new ControllerModule(router))
          .build();
    }
    return controllerComponent;
  }

  @Override public DrawerLayout getDrawerLayout() {
    return drawerLayout;
  }
}