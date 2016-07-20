package gregpearce.archivorg.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lapism.searchview.SearchView;

import butterknife.BindView;
import butterknife.ButterKnife;
import gregpearce.archivorg.R;

public class MainActivity extends BaseActivity {

  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.view_pager) ViewPager viewPager;
  @BindView(R.id.tab_layout) TabLayout tabLayout;
  @BindView(R.id.search_view) SearchView searchView;
  @BindView(R.id.navigation_view) NavigationView navigationView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setupToolbar();
    setupTabs();
    setupSearchView();
  }

  private void setupToolbar() {
    toolbar.setNavigationContentDescription(getResources().getString(R.string.app_name));
    toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
    setSupportActionBar(toolbar);
  }

  private void setupTabs() {
    MainPagerAdapter sectionsPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(sectionsPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  protected void setupSearchView() {
    searchView.setVersion(SearchView.VERSION_MENU_ITEM);
    searchView.setVersionMargins(SearchView.VERSION_MARGINS_MENU_ITEM);
    searchView.setTheme(SearchView.THEME_LIGHT, true);
    searchView.setHint(R.string.search);
    searchView.setTextSize(16);
    searchView.setHint("Search");
    searchView.setDivider(false);
    searchView.setVoice(true);
    searchView.setVoiceText("Set permission on Android 6+ !");
    searchView.setAnimationDuration(SearchView.ANIMATION_DURATION);
    searchView.setShadow(false);
    searchView.setShadowColor(ContextCompat.getColor(this, R.color.search_shadow_layout));
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_search:
        searchView.open(true);
        return true;
      case android.R.id.home:
        drawerLayout.openDrawer(GravityCompat.START);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
