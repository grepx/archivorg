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
import android.widget.Toast;
import butterknife.BindView;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.feed.FeedPresenter;
import gregpearce.archivorg.domain.feed.FeedViewState;
import gregpearce.archivorg.domain.model.FeedItem;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.domain.network.FeedServiceFactory;
import gregpearce.archivorg.ui.BaseController;
import gregpearce.archivorg.ui.model.StateTransition;
import gregpearce.archivorg.util.BundleBuilder;
import java.util.List;
import javax.inject.Inject;

public class FeedController extends BaseController {

  @Inject FeedServiceFactory feedServiceFactory;

  private FeedPresenter presenter;
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
    presenter.getViewState()
             .map(viewState -> new StateTransition<>(viewState))
             .scan((transition, nextState) -> transition.next(nextState))
             .subscribe(
                 transition -> {
                   processTransition(transition.old(), transition.current());
                 },
                 throwable -> {
                   throw new RuntimeException(throwable);
                 });
  }

  private void processTransition(FeedViewState old, FeedViewState current) {
    if (old == null) {
      setupView(current);
      return;
    }
    if (old.refreshing() != current.refreshing()) {
      setRefreshing(current.refreshing());
    }
    if (old.feedItems() != current.feedItems() ||
        old.showBottomLoading() != current.showBottomLoading()) {
      setFeed(current.feedItems(), current.showBottomLoading());
    }
    if (old.showError() != current.showError()) {
      showError();
    }
  }

  private void setupView(FeedViewState viewState) {
    setFeed(viewState.feedItems(), viewState.showBottomLoading());
    setRefreshing(viewState.refreshing());
    if (viewState.showError()) {
      showError();
    }
  }

  @Override protected void onDetach(@NonNull View view) {
  }

  public void setRefreshing(boolean isRefreshing) {
    swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(isRefreshing));
  }

  public void setFeed(List<FeedItem> feedItems, boolean showBottomLoading) {
    adapter.updateFeed(feedItems, showBottomLoading);
    if (feedItems.size() > 0) {
      emptyMessageTextView.setVisibility(View.GONE);
    } else {
      emptyMessageTextView.setVisibility(View.VISIBLE);
    }
  }

  public void showError() {
    Toast.makeText(getActivity(), R.string.error_network, Toast.LENGTH_LONG).show();
  }
}
