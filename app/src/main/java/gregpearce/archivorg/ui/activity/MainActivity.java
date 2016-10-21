package gregpearce.archivorg.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
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
import gregpearce.archivorg.ui.bookmarks.BookmarkController;
import gregpearce.archivorg.ui.discover.DiscoverController;
import java.util.List;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements DrawerLayoutProvider {

  @BindView(R.id.controller_container) ViewGroup container;
  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

  private Router router;

  private enum RootController {Discover, Bookmarks, Downloads, Settings}

  private RootController rootController;

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
      rootController = RootController.Discover;
      navigationView.getMenu().findItem(R.id.drawer_discover).setChecked(true);
      router.setRoot(RouterTransaction.with(new DiscoverController()));
    }
  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(menuItem -> {
      navigationView.getMenu().findItem(R.id.drawer_discover).setChecked(false);
      navigationView.getMenu().findItem(R.id.drawer_bookmarks).setChecked(false);
      navigationView.getMenu().findItem(R.id.drawer_downloads).setChecked(false);
      navigationView.getMenu().findItem(R.id.drawer_settings).setChecked(false);

      switch (menuItem.getItemId()) {
        case R.id.drawer_discover:
          if (rootController != RootController.Discover) {
            rootController = RootController.Discover;
            router.setRoot(RouterTransaction.with(new DiscoverController()));
          }
          break;
        case R.id.drawer_bookmarks:
          if (rootController != RootController.Bookmarks) {
            rootController = RootController.Bookmarks;
            router.setRoot(RouterTransaction.with(new BookmarkController()));
          }
          break;
        case R.id.drawer_downloads:
          if (rootController != RootController.Downloads) {
            rootController = RootController.Downloads;
            //router.setRoot(RouterTransaction.with(new DiscoverController()));
          }
          break;
        case R.id.drawer_settings:
          if (rootController != RootController.Settings) {
            rootController = RootController.Settings;
            //router.setRoot(RouterTransaction.with(new DiscoverController()));
          }
          break;
        default:
          Timber.e("Unknown drawer option");
          break;
      }
      // Close the navigation drawer when an item is selected.
      menuItem.setChecked(true);
      drawerLayout.closeDrawers();
      return true;
    });
  }

  @Override public void onBackPressed() {
    if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else if (!router.handleBack()) {
      super.onBackPressed();
    }
  }

  public BaseController getActivityController() {
    List<RouterTransaction> backstack = router.getBackstack();
    return (BaseController) backstack.get(backstack.size() - 1).controller();
  }

  @Override public DrawerLayout getDrawerLayout() {
    return drawerLayout;
  }
}