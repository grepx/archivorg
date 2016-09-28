package gregpearce.archivorg.ui.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.feed.FeedPresenter;
import gregpearce.archivorg.domain.model.FeedItem;
import java.util.Collections;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private FeedPresenter presenter;
  private List<FeedItem> feedItems = Collections.EMPTY_LIST;
  private boolean showBottomLoading;
  private boolean showError;
  private int count = 0;

  private static final int VIEW_TYPE_FEED_ITEM = 1;
  private static final int VIEW_TYPE_FEED_LOADING = 2;
  private static final int VIEW_TYPE_FEED_ERROR = 3;

  public FeedAdapter(FeedPresenter presenter) {
    this.presenter = presenter;
  }

  void  updateFeed(List<FeedItem> feedItems, boolean showBottomLoading, boolean showError) {
    this.feedItems = feedItems;
    this.showBottomLoading = showBottomLoading;
    this.showError = showError;

    count = feedItems.size();
    if (showBottomLoading || showError) {
      count++;
    }

    notifyDataSetChanged();
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    if (i == VIEW_TYPE_FEED_ITEM) {
      View view = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.feed_item, viewGroup, false);
      return new FeedItemViewHolder(view);
    } else if (i == VIEW_TYPE_FEED_LOADING) {
      View view = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.feed_loading, viewGroup, false);
      return new FeedLoadingViewHolder(view);
    } else {
      // default: show error tap to retry
      View view = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.tap_to_retry, viewGroup, false);
      return new FeedErrorViewHolder(view, presenter);
    }
  }

  @Override public int getItemViewType(int position) {
    boolean isLastItem = position == count - 1;
    if (isLastItem && showBottomLoading) {
      return VIEW_TYPE_FEED_LOADING;
    }
    if (isLastItem && showError) {
      return VIEW_TYPE_FEED_ERROR;
    }
    return VIEW_TYPE_FEED_ITEM;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    if (viewHolder instanceof FeedItemViewHolder) {
      FeedItemViewHolder feedItemViewHolder = (FeedItemViewHolder) viewHolder;
      presenter.scrolledToIndex(position);
      FeedItem feedItem = feedItems.get(position);
      feedItemViewHolder.update(feedItem);
    }
  }

  @Override public int getItemCount() {
    return count;
  }
}
