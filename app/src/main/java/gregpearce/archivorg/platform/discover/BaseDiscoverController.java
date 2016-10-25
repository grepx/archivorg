package gregpearce.archivorg.platform.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
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
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.domain.search.SearchBarPresenter;
import gregpearce.archivorg.domain.search.SearchBarPresenterFactory;
import gregpearce.archivorg.platform.BaseController;
import gregpearce.archivorg.platform.OverlayChildRouter;
import gregpearce.archivorg.platform.feed.FeedController;
import gregpearce.archivorg.platform.util.ArSearchView;
import javax.inject.Inject;

public abstract class BaseDiscoverController extends BaseController implements OverlayChildRouter {
  @Inject Navigator navigator;
  @Inject SearchBarPresenterFactory searchBarPresenterFactory;

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
  protected abstract FeedController getController(FeedContentType feedContentType);

  private void setupTabs() {
    FeedTab[] tabs = {
        new FeedTab(R.string.all, FeedContentType.All, getController(FeedContentType.All)),
        new FeedTab(R.string.audio, FeedContentType.Audio, getController(FeedContentType.Audio)),
        new FeedTab(R.string.video, FeedContentType.Video, getController(FeedContentType.Video)),
        new FeedTab(R.string.text, FeedContentType.Book, getController(FeedContentType.Book)),
        new FeedTab(R.string.images, FeedContentType.Image, getController(FeedContentType.Image)),
        };
    pagerAdapter = new PagerAdapter(this, tabs);
    viewPager.setAdapter(pagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  protected void setupSearchView() {
    searchView.setHint(R.string.search_hint);
    searchView.setPresenter(searchBarPresenterFactory.create(FeedType.Search));
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

  public void pushOverlayController(RouterTransaction transaction) {
    getChildRouter(modalContainer, "OVERLAY_ROUTER")
        .setPopsLastView(true)
        .setRoot(transaction);
  }
}