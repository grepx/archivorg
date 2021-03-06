package gregpearce.archivorg.platform.bookmarks;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.domain.search.SearchBarPresenter;
import gregpearce.archivorg.domain.search.SearchBarPresenterFactory;
import gregpearce.archivorg.platform.BaseController;
import gregpearce.archivorg.platform.feed.FeedController;
import gregpearce.archivorg.platform.util.ArSearchView;
import javax.inject.Inject;

public class BookmarkController extends BaseController {
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.feed_container) ViewGroup feedContainer;
  @BindView(R.id.search_view) ArSearchView searchView;

  @Inject SearchBarPresenterFactory searchBarPresenterFactory;

  public BookmarkController() {
  }

  public BookmarkController(Bundle args) {
    super(args);
  }

  @Override protected void onCreate() {
    getComponent().inject(this);
    setHasOptionsMenu(true);
  }

  @Override protected View inflateView(@NonNull LayoutInflater inflater,
                                       @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.controller_search, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    setupSearchView();

    setActionBar(toolbar);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
    getActionBar().setTitle(R.string.bookmarks);

    FeedController feedController =
        new FeedController(FeedType.Bookmarks, FeedContentType.All, null);
    getChildRouter(feedContainer, null).setRoot(RouterTransaction.with(feedController));
  }

  @Override protected void onAttach(@NonNull View view) {
    super.onAttach(view);
    getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
  }

  private void setupSearchView() {
    searchView.setHint(R.string.search_hint_bookmarks);
    searchView.setPresenter(searchBarPresenterFactory.create(FeedType.BookmarksSearch));
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
}