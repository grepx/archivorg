package gregpearce.archivorg.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lapism.searchview.SearchView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import gregpearce.archivorg.R;
import gregpearce.archivorg.ui.feed.FeedPresenter;
import gregpearce.archivorg.ui.feed.FeedPresenterFactory;
import gregpearce.archivorg.ui.feed.FeedType;

public class MainActivity extends BaseActivity {
  @Inject FeedPresenterFactory feedPresenterFactory;
  FeedPresenter popularFeedPresenter;
  FeedPresenter videoFeedPresenter;
  FeedPresenter audioFeedPresenter;
  FeedPresenter bookFeedPresenter;
  FeedPresenter imageFeedPresenter;

  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.view_pager) ViewPager viewPager;
  @BindView(R.id.tab_layout) TabLayout tabLayout;
  @BindView(R.id.search_view) SearchView searchView;
  @BindView(R.id.navigation_view) NavigationView navigationView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    getComponent().inject(this);
    ButterKnife.bind(this);

    setupPresenters();
    setupToolbar();
    setupTabs();
    setupSearchView();
  }
  
  private void setupPresenters() {
    popularFeedPresenter = feedPresenterFactory.get(FeedType.Popular);
    videoFeedPresenter = feedPresenterFactory.get(FeedType.Video);
    audioFeedPresenter = feedPresenterFactory.get(FeedType.Audio);
    imageFeedPresenter = feedPresenterFactory.get(FeedType.Book);
    bookFeedPresenter = feedPresenterFactory.get(FeedType.Image);
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
    searchView.setHint("Search Archive.org");
    searchView.setDivider(false);
    searchView.setVoice(true);
    searchView.setVoiceText("Set permission on Android 6+ !");
    searchView.setAnimationDuration(SearchView.ANIMATION_DURATION);
    // shadow = modal behavior, possibly revert back to this in the future
    searchView.setShadow(false);
//    searchView.setShadowColor(ContextCompat.getColor(this, R.color.search_shadow_layout));
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        popularFeedPresenter.search(query);
        videoFeedPresenter.search(query);
        audioFeedPresenter.search(query);
        imageFeedPresenter.search(query);
        bookFeedPresenter.search(query);
        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
    searchView.setOnOpenCloseListener(new SearchView.OnOpenCloseListener() {
      @Override public void onClose() {
        popularFeedPresenter.search("");
        videoFeedPresenter.search("");
        audioFeedPresenter.search("");
        imageFeedPresenter.search("");
        bookFeedPresenter.search("");
      }

      @Override public void onOpen() {
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
