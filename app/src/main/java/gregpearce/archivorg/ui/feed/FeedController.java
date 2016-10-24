package gregpearce.archivorg.ui.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.feed.FeedPresenter;
import gregpearce.archivorg.domain.feed.FeedPresenterFactory;
import gregpearce.archivorg.domain.feed.FeedView;
import gregpearce.archivorg.domain.feed.FeedViewState;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.domain.service.FeedService;
import gregpearce.archivorg.domain.service.FeedServiceFactory;
import gregpearce.archivorg.ui.PresenterController;
import gregpearce.archivorg.util.BundleBuilder;
import javax.inject.Inject;

import static gregpearce.archivorg.util.ViewUtil.setVisible;

public class FeedController extends PresenterController implements FeedView {

  @Inject FeedServiceFactory feedServiceFactory;
  @Inject FeedPresenterFactory feedPresenterFactory;

  private FeedPresenter presenter;
  private FeedViewState viewState;

  private FeedType feedType;
  private FeedContentType feedContentType;
  private String query;

  private FeedAdapter adapter;
  @BindView(R.id.recycler_view) RecyclerView recyclerView;
  @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R.id.empty_message_text_view) TextView emptyMessageTextView;

  private static final String ARGUMENT_FEED_TYPE = "ARGUMENT_FEED_TYPE";
  private static final String ARGUMENT_FEED_CONTENT_TYPE = "ARGUMENT_FEED_CONTENT_TYPE";
  private static final String ARGUMENT_QUERY = "ARGUMENT_QUERY";

  public static FeedController createFeedInstance(FeedType feedType,
                                                  FeedContentType feedContentType, String query) {
    return new FeedController(feedType, feedContentType, query);
  }

  public FeedController(FeedType feedType, FeedContentType feedContentType, String query) {
    this(BundleBuilder.create()
                      .putSerializable(ARGUMENT_FEED_TYPE, feedType)
                      .putSerializable(ARGUMENT_FEED_CONTENT_TYPE, feedContentType)
                      .putString(ARGUMENT_QUERY, query)
                      .build());
  }

  public FeedController(Bundle args) {
    super(args);
    feedType = (FeedType) args.getSerializable(ARGUMENT_FEED_TYPE);
    feedContentType = (FeedContentType) args.getSerializable(ARGUMENT_FEED_CONTENT_TYPE);
    query = args.getString(ARGUMENT_QUERY);
  }

  @Override protected void onCreate() {
    getComponent().inject(this);
    FeedService feedService = feedServiceFactory.get(feedType, feedContentType, query);
    presenter = feedPresenterFactory.create(feedService);
    registerPresenter(presenter);
  }

  @Override
  protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.controller_feed, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    adapter = new FeedAdapter(getComponent(), presenter);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setAdapter(adapter);
    swipeRefreshLayout.setOnRefreshListener(() -> presenter.refresh());
  }

  private void setupView() {
    updateRefreshing();
    updateFeed();
  }

  @Override public void update(FeedViewState updatedViewState) {
    FeedViewState oldViewState = viewState;
    viewState = updatedViewState;

    if (oldViewState.showRefreshing() != viewState.showRefreshing()) {
      updateRefreshing();
    }
    if (oldViewState.feedItems() != viewState.feedItems() ||
        oldViewState.showBottomLoading() != viewState.showBottomLoading()) {
      updateFeed();
    }
    if (oldViewState.showEmptyFeedMessage() != viewState.showEmptyFeedMessage()) {
      setVisible(viewState.showEmptyFeedMessage(), emptyMessageTextView);
    }
    if (oldViewState.showError() != viewState.showError()) {
      updateFeed();
    }
  }

  private void updateRefreshing() {
    swipeRefreshLayout.setRefreshing(viewState.showRefreshing());
  }

  private void updateFeed() {
    adapter.updateFeed(viewState.feedItems(), viewState.showBottomLoading(), viewState.showError());
  }

  @Override protected void onAttach(@NonNull View view) {
    viewState = presenter.subscribe(this);
    setupView();
  }

  @Override protected void onDetach(@NonNull View view) {
    presenter.unsubscribe();
  }
}