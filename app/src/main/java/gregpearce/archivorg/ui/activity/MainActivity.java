package gregpearce.archivorg.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import gregpearce.archivorg.R;
import gregpearce.archivorg.di.ControllerComponent;
import gregpearce.archivorg.ui.BaseController;
import gregpearce.archivorg.ui.OverlayChildRouter;
import gregpearce.archivorg.ui.discover.DiscoverController;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DrawerLayoutProvider {

  @BindView(R.id.controller_container) ViewGroup container;
  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

  private Router router;

  private ControllerComponent controllerComponent;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    // Set up the navigation drawer.
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

  @Override public void onBackPressed() {
    if (!router.handleBack()) {
      super.onBackPressed();
    }
  }

  public BaseController getActivityController() {
    List<RouterTransaction> backstack = router.getBackstack();
    return (BaseController) backstack.get(backstack.size()-1).controller();
  }

  @Override public DrawerLayout getDrawerLayout() {
    return drawerLayout;
  }
}
