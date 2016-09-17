package gregpearce.archivorg.ui.activity;

import android.content.Context;
import android.content.Intent;
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
import gregpearce.archivorg.di.ActivityComponent;
import gregpearce.archivorg.di.ActivityModule;
import gregpearce.archivorg.di.DaggerActivityComponent;
import gregpearce.archivorg.ui.detail.DetailController;

public class MainActivity extends AppCompatActivity
    implements ActionBarProvider, DrawerLayoutProvider, ActivityComponentProvider {

  private static String INTENT_EXTRA_ID = "INTENT_EXTRA_ID";

  public static Intent getCallingIntent(Context context, String id) {
    Intent intent = new Intent(context, MainActivity.class);
    intent.putExtra(INTENT_EXTRA_ID, id);
    return intent;
  }

  @BindView(R.id.controller_container) ViewGroup container;
  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

  private Router router;

  private ActivityComponent activityComponent;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    // Set up the toolbar.
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

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
      String id = getIntent().getExtras().getString(INTENT_EXTRA_ID);
      router.setRoot(RouterTransaction.with(new DetailController(id)));
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

  @Override public ActivityComponent getActivityComponent() {
    if (activityComponent == null) {
      activityComponent = DaggerActivityComponent.builder()
          .applicationComponent(MainApplication.APP_COMPONENT)
          .activityModule(new ActivityModule(this))
          .build();
    }
    return activityComponent;
  }

  @Override public DrawerLayout getDrawerLayout() {
    return drawerLayout;
  }
}
