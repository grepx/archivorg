package gregpearce.archivorg.ui.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.lapism.searchview.SearchView;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.ui.ActivityController;
import gregpearce.archivorg.ui.feed.FeedController;
import gregpearce.archivorg.domain.model.FeedType;
import javax.inject.Inject;

public abstract class BaseDiscoverController extends ActivityController {
  @Inject Navigator navigator;

  protected PagerAdapter pagerAdapter;

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.view_pager) ViewPager viewPager;
  @BindView(R.id.tab_layout) TabLayout tabLayout;
  @BindView(R.id.search_view) SearchView searchView;

  public BaseDiscoverController() {
  }

  public BaseDiscoverController(Bundle args) {
    super(args);
  }

  @Override protected void onCreate() {
    getComponent().inject(this);
  }

  @Override
  protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.controller_discover, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);

    setupTabs();
    setupSearchView();

    setActionBar(toolbar);
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

  private void setupSearchView() {
    searchView.setVersion(SearchView.VERSION_TOOLBAR);
    searchView.setVersionMargins(SearchView.VERSION_MARGINS_TOOLBAR_BIG);
    searchView.setTheme(SearchView.THEME_LIGHT, true);
    searchView.setVoice(false);
    searchView.setHint("Search Archive.org");

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String query) {
        searchView.close(false);

        search(query);
        return true;
      }

      @Override public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
  }

  private void search(String query) {
    int tabPosition = viewPager.getCurrentItem();
    FeedType feedType = pagerAdapter.getFeedType(tabPosition);
    navigator.navigateToSearch(feedType, query);
  }
}