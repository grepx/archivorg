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
import gregpearce.archivorg.domain.feed.FeedPresenterFactory;
import gregpearce.archivorg.domain.feed.FeedView;
import gregpearce.archivorg.domain.model.FeedItem;
import gregpearce.archivorg.ui.BaseController;
import gregpearce.archivorg.util.BundleBuilder;
import java.util.List;
import javax.inject.Inject;

public class FeedFragment extends BaseController implements FeedView {

  @Inject FeedPresenterFactory feedPresenterFactory;

  private FeedPresenter presenter;
  private FeedAdapter adapter;
  private FeedType feedType;

  @BindView(R.id.recycler_view) RecyclerView recyclerView;
  @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R.id.empty_message_text_view) TextView emptyMessageTextView;

  private static final String ARGUMENT_TYPE = "ARGUMENT_TYPE";

  public FeedFragment(FeedType feedType) {
    this(new BundleBuilder().putSerializable(ARGUMENT_TYPE, feedType).build());
  }

  public FeedFragment(Bundle args) {
    super(args);
    feedType = (FeedType) args.getSerializable(ARGUMENT_TYPE);
  }

  @Override protected void onCreate() {
    getComponent().inject(this);
    presenter = feedPresenterFactory.get(feedType);
  }

  @Override
  protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.fragment_feed, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new FeedAdapter(presenter);
    recyclerView.setAdapter(adapter);

    swipeRefreshLayout.setOnRefreshListener(() -> presenter.refresh());
  }

  @Override protected void onAttach(@NonNull View view) {
    presenter.registerView(this);
    presenter.resume();
  }

  @Override protected void onDetach(@NonNull View view) {
    presenter.unregisterView();
    presenter.pause();
  }

  @Override public void updateRefreshing(boolean isRefreshing) {
    swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(isRefreshing));
  }

  @Override public void updateFeed(List<FeedItem> feedItems, boolean endOfFeed) {
    adapter.updateFeed(feedItems, endOfFeed);
    if (feedItems.size() > 0) {
      emptyMessageTextView.setVisibility(View.GONE);
    } else {
      emptyMessageTextView.setVisibility(View.VISIBLE);
    }
  }

  @Override public void showError() {
    Toast.makeText(getActivity(), R.string.error_network, Toast.LENGTH_LONG).show();
  }
}
