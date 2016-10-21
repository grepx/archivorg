package gregpearce.archivorg.ui.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.bluelinelabs.conductor.RouterTransaction;
import com.lapism.searchview.SearchView;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.ui.BaseController;
import gregpearce.archivorg.ui.OverlayChildRouter;
import gregpearce.archivorg.ui.feed.FeedController;
import gregpearce.archivorg.ui.util.ArSearchView;
import javax.inject.Inject;

public abstract class BaseDiscoverController extends BaseController implements OverlayChildRouter {
  @Inject Navigator navigator;

  protected PagerAdapter pagerAdapter;

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.view_pager) ViewPager viewPager;
  @BindView(R.id.tab_layout) TabLayout tabLayout;
  @BindView(R.id.search_view) ArSearchView searchView;
  @BindView(R.id.modal_controller_container) ViewGroup modalContainer;

  public BaseDiscoverController() {
  }

  public BaseDiscoverController(Bundle args) {
    super(args);
  }

  @Override protected void onCreate() {
    getComponent().inject(this);
    setHasOptionsMenu(true);
  }

  @Override
  protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.controller_discover, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    setupTabs();
    setupSearchView();
  }

  @Override protected void onAttach(@NonNull View view) {
    super.onAttach(view);

    setActionBar(toolbar);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
    getActionBar().setTitle(R.string.discover);

    getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
  }

  /**
   * Hook in point where derived classes can provide different behavior.
   *
   * @return A {@link FeedController} for this tab.
   */
  protected abstract FeedController getController(FeedType feedType);

  private void setupTabs() {
    FeedTab[] tabs = {
        new FeedTab("ALL", FeedType.All, getController(FeedType.All)),
        new FeedTab("AUDIO", FeedType.Audio, getController(FeedType.Audio)),
        new FeedTab("VIDEO", FeedType.Video, getController(FeedType.Video)),
        new FeedTab("TEXT", FeedType.Book, getController(FeedType.Book)),
        new FeedTab("IMAGES", FeedType.Image, getController(FeedType.Image)),
        };
    pagerAdapter = new PagerAdapter(this, tabs);
    viewPager.setAdapter(pagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  protected void setupSearchView() {
    searchView.setHint("Search Archive.org");
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        search(query);
        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_discover, menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        getDrawerLayout().openDrawer(Gravity.LEFT);
        return true;
      case R.id.action_search:
        searchView.open(true);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void search(String query) {
    int tabPosition = viewPager.getCurrentItem();
    FeedType feedType = pagerAdapter.getFeedType(tabPosition);
    navigator.navigateToSearch(feedType, query);
  }

  public void pushOverlayController(RouterTransaction transaction) {
    getChildRouter(modalContainer, "OVERLAY_ROUTER")
        .setPopsLastView(true)
        .setRoot(transaction);
  }
}