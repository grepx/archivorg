package gregpearce.archivorg.platform.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.bluelinelabs.conductor.RouterTransaction;
import com.lapism.searchview.SearchView;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.domain.search.SearchBarPresenter;
import gregpearce.archivorg.domain.search.SearchBarPresenterFactory;
import gregpearce.archivorg.platform.BaseController;
import gregpearce.archivorg.platform.feed.FeedController;
import gregpearce.archivorg.platform.util.ArSearchView;
import gregpearce.archivorg.platform.util.BundleBuilder;
import javax.inject.Inject;

public class SearchController extends BaseController {
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.feed_container) ViewGroup feedContainer;
  @BindView(R.id.search_view) ArSearchView searchView;

  @Inject SearchBarPresenterFactory searchBarPresenterFactory;

  private static final String ARGUMENT_FEED_TYPE = "ARGUMENT_FEED_TYPE";
  private static final String ARGUMENT_CONTENT_TYPE = "ARGUMENT_CONTENT_TYPE";
  private static final String ARGUMENT_QUERY = "ARGUMENT_QUERY";

  private final FeedType feedType;
  private final FeedContentType feedContentType;
  private final String query;

  public SearchController(FeedType feedType, FeedContentType contentType, String query) {
    this(BundleBuilder.create()
                      .putSerializable(ARGUMENT_FEED_TYPE, feedType)
                      .putSerializable(ARGUMENT_CONTENT_TYPE, contentType)
                      .putString(ARGUMENT_QUERY, query)
                      .build());
  }

  public SearchController(Bundle args) {
    super(args);
    feedType = (FeedType) args.getSerializable(ARGUMENT_FEED_TYPE);
    feedContentType = (FeedContentType) args.getSerializable(ARGUMENT_CONTENT_TYPE);
    query = args.getString(ARGUMENT_QUERY);
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

    getChildRouter(feedContainer, null).setRoot(RouterTransaction.with(getController()));
  }

  protected FeedController getController() {
    return FeedController.createFeedInstance(feedType, feedContentType, query);
  }

  @Override protected void onAttach(@NonNull View view) {
    super.onAttach(view);
    getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
  }

  private void setupSearchView() {
    if (feedType == FeedType.BookmarksSearch) {
      searchView.setHint(R.string.search_hint_bookmarks);
    } else {
      searchView.setHint(R.string.search_hint);
    }
    searchView.setPresenter(searchBarPresenterFactory.create(feedType));
    searchView.setVersion(SearchView.VERSION_TOOLBAR);
    searchView.setVersionMargins(SearchView.VERSION_MARGINS_TOOLBAR_BIG);
    searchView.setShadow(false);
    searchView.setTextInput(query);
    searchView.setOnMenuClickListener(() -> getRouter().handleBack());
  }
}