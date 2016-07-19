package gregpearce.archivorg.ui.feed;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import gregpearce.archivorg.R;
import gregpearce.archivorg.ui.BaseFragment;

public class FeedFragment extends BaseFragment implements FeedView {

  @Inject FeedPresenter presenter;
  private FeedAdapter adapter;

  @BindView(R.id.recycler_view) RecyclerView recyclerView;
  @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
    ButterKnife.bind(this, rootView);

    getComponent().inject(this);
    presenter.registerView(this);

    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new FeedAdapter(presenter);
    recyclerView.setAdapter(adapter);

    swipeRefreshLayout.setOnRefreshListener(() -> presenter.refresh());

    return rootView;
  }

  @Override public void onStart() {
    super.onStart();
    presenter.refresh();
  }

  @Override public void updateRefreshing(boolean isRefreshing) {
    swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(isRefreshing));
  }

  @Override public void updateFeed(List<FeedItem> feedItems, boolean endOfFeed) {
    adapter.updateFeed(feedItems, endOfFeed);
  }

  @Override public void showError(String error) {

  }
}
