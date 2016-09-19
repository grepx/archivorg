package gregpearce.archivorg.ui.discover;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.bluelinelabs.conductor.support.ControllerPagerAdapter;
import com.lapism.searchview.SearchView;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.discover.DiscoverPresenter;
import gregpearce.archivorg.ui.BaseController;
import javax.inject.Inject;

public class DiscoverController extends BaseController {
  @Inject DiscoverPresenter discoverPresenter;

  private ControllerPagerAdapter pagerAdapter;

  @BindView(R.id.view_pager) ViewPager viewPager;
  @BindView(R.id.tab_layout) TabLayout tabLayout;
  @BindView(R.id.search_view) SearchView searchView;
  @BindView(R.id.navigation_view) NavigationView navigationView;

  @Override protected void onCreate() {
    pagerAdapter = new DiscoverPagerAdapter(this);
  }

  @Override
  protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.controller_discover, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    setupTabs();
    setupSearchView();
  }

  private void setupTabs() {
    viewPager.setAdapter(pagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  protected void setupSearchView() {
    searchView.setVersion(SearchView.VERSION_MENU_ITEM);
    searchView.setVersionMargins(SearchView.VERSION_MARGINS_MENU_ITEM);
    searchView.setTheme(SearchView.THEME_LIGHT, true);
    searchView.setHint(R.string.search);
    searchView.setTextSize(16);
    searchView.setHint("Search Archive.org");
    searchView.setDivider(false);
    searchView.setVoice(false);
    searchView.setVoiceText("Set permission on Android 6+ !");
    searchView.setAnimationDuration(SearchView.ANIMATION_DURATION);
    // shadow = modal behavior, possibly revert back to this in the future
    searchView.setShadow(false);
    //    searchView.setShadowColor(ContextCompat.getColor(this, R.color.search_shadow_layout));
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String query) {
        discoverPresenter.search(query);
        return true;
      }

      @Override public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
    searchView.setOnOpenCloseListener(new SearchView.OnOpenCloseListener() {
      // for some reason, onClose is called one time during initialisation, code around it
      private boolean initCall = true;

      @Override public void onClose() {
        if (!initCall) {
          discoverPresenter.onCloseSearch();
        } else {
          initCall = false;
        }
      }

      @Override public void onOpen() {
      }
    });
  }

  @Override protected void onAttach(@NonNull View view) {
    super.onAttach(view);
    setupToolbar();
  }

  private void setupToolbar() {
    ActionBar actionBar = getActionBar();
    actionBar.setTitle(getResources().getString(R.string.app_name));
    actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
    actionBar.setDisplayHomeAsUpEnabled(true);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_main, menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_search:
        searchView.open(true);
        return true;
      case android.R.id.home:
        getDrawerLayout().openDrawer(GravityCompat.START);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
