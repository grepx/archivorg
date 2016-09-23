package gregpearce.archivorg.ui.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.feed.FeedPresenter;
import gregpearce.archivorg.domain.feed.FeedView;
import gregpearce.archivorg.domain.feed.FeedViewState;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.domain.network.FeedServiceFactory;
import gregpearce.archivorg.ui.BaseController;
import gregpearce.archivorg.util.BundleBuilder;
import gregpearce.archivorg.util.ViewUtil;
import javax.inject.Inject;

public class FeedController extends BaseController implements FeedView {

  @Inject FeedServiceFactory feedServiceFactory;

  private FeedPresenter presenter;
  private FeedViewState viewState;

  private FeedType feedType;
  private String query;

  private FeedAdapter adapter;
  @BindView(R.id.recycler_view) RecyclerView recyclerView;
  @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R.id.empty_message_text_view) TextView emptyMessageTextView;

  private static final String ARGUMENT_FEED_TYPE = "ARGUMENT_FEED_TYPE";
  private static final String ARGUMENT_QUERY = "ARGUMENT_QUERY";

  public static FeedController createTopFeedInstance(FeedType feedType) {
    return new FeedController(feedType, null);
  }

  public static FeedController createSearchFeedInstance(FeedType feedType, String query) {
    return new FeedController(feedType, query);
  }

  private FeedController(FeedType feedType, String query) {
    this(BundleBuilder.create()
                      .putSerializable(ARGUMENT_FEED_TYPE, feedType)
                      .putString(ARGUMENT_QUERY, query)
                      .build());
  }

  public FeedController(Bundle args) {
    super(args);
    feedType = (FeedType) args.getSerializable(ARGUMENT_FEED_TYPE);
    query = args.getString(ARGUMENT_QUERY);
  }

  @Override protected void onCreate() {
    getComponent().inject(this);
    if (query != null) {
      presenter = new FeedPresenter(feedServiceFactory.getSearchFeed(feedType, query));
    } else {
      presenter = new FeedPresenter(feedServiceFactory.getTopFeed(feedType));
    }
  }

  @Override
  protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.controller_feed, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    adapter = new FeedAdapter(presenter);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setAdapter(adapter);
    swipeRefreshLayout.setOnRefreshListener(() -> presenter.refresh());
  }

  @Override protected void onAttach(@NonNull View view) {
    viewState = presenter.subscribe(this);
    setupView();
  }

  @Override protected void onDetach(@NonNull View view) {
    presenter.unsubscribe();
  }

  @Override public void update(FeedViewState updatedViewState) {
    FeedViewState oldViewState = viewState;
    viewState = updatedViewState;
    if (oldViewState.refreshing() != viewState.refreshing()) {
      updateRefreshing();
    }
    if (oldViewState.feedItems() != viewState.feedItems() ||
        oldViewState.showBottomLoading() != viewState.showBottomLoading()) {
      updateFeed();
    }
    if (oldViewState.showEmptyFeedMessage() != viewState.showEmptyFeedMessage()) {
      ViewUtil.setVisible(emptyMessageTextView, viewState.showEmptyFeedMessage());
    }
    if (oldViewState.showError() != viewState.showError()) {
      updateError();
    }
  }

  private void setupView() {
    updateFeed();
    updateRefreshing();
    updateError();
  }

  public void updateRefreshing() {
    swipeRefreshLayout.setRefreshing(viewState.refreshing());
  }

  public void updateFeed() {
    adapter.updateFeed(viewState.feedItems(), viewState.showBottomLoading());
  }

  public void updateError() {
    if (viewState.showError()) {
      Toast.makeText(getActivity(), R.string.error_network, Toast.LENGTH_LONG).show();
    }
  }
}
