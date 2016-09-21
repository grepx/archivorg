package gregpearce.archivorg.ui.discover;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
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

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.view_pager) ViewPager viewPager;
  @BindView(R.id.tab_layout) TabLayout tabLayout;
  @BindView(R.id.search_view) SearchView searchView;

  @Override protected void onCreate() {
    getComponent().inject(this);
  }

  @Override
  protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.controller_discover, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    pagerAdapter = new DiscoverPagerAdapter(this);

    setupTabs();
    setupSearchView();

    setActionBar(toolbar);
  }

  private void setupTabs() {
    viewPager.setAdapter(pagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  protected void setupSearchView() {
    searchView.setVersion(SearchView.VERSION_TOOLBAR);
    searchView.setNavigationIconArrowHamburger();
    searchView.setVersionMargins(SearchView.VERSION_MARGINS_TOOLBAR_BIG);
    searchView.setTheme(SearchView.THEME_LIGHT, true);
    searchView.setTextSize(16);
    searchView.setHint("Search Archive.org");
    searchView.setDivider(false);
    searchView.setVoice(false);
    searchView.setShadow(false);
    searchView.setAnimationDuration(SearchView.ANIMATION_DURATION);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String query) {
        discoverPresenter.search(query);
        searchView.setShadow(false);
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
        searchView.setShadow(false);
      }

      @Override public void onOpen() {
        searchView.setShadow(true);
      }
    });
    searchView.setOnMenuClickListener(() -> getDrawerLayout().openDrawer(GravityCompat.START));
  }

  @Override protected void onAttach(@NonNull View view) {
    super.onAttach(view);
    getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
  }

  private static final String INSTANCE_STATE_SEARCH_VIEW = "INSTANCE_STATE_SEARCH_VIEW";

  @Override protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(INSTANCE_STATE_SEARCH_VIEW, searchView.onSaveInstanceState());
  }

  @Override protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    searchView.onRestoreInstanceState(savedInstanceState.getParcelable(INSTANCE_STATE_SEARCH_VIEW));
  }
}
