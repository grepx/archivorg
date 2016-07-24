package gregpearce.archivorg.ui.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.FeedPresenter;
import gregpearce.archivorg.model.FeedItem;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private FeedPresenter presenter;
  private List<FeedItem> feedItems = new ArrayList<>(0);
  private boolean endOfFeed;
  private int count = 0;

  private static final int VIEW_TYPE_FEED_ITEM = 1;
  private static final int VIEW_TYPE_BOTTOM_LOAD = 2;

  public FeedAdapter(FeedPresenter presenter) {
    this.presenter = presenter;
  }

  void updateFeed(List<FeedItem> feedItems, boolean endOfFeed) {
    this.feedItems = feedItems;
    this.endOfFeed = endOfFeed;
    count = feedItems.size() + (endOfFeed ? 0 : 1);
    notifyDataSetChanged();
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    if (i == VIEW_TYPE_FEED_ITEM) {
      View feedItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_item, viewGroup, false);
      return new FeedItemViewHolder(feedItemView);
    } else {
      View bottomLoadView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bottom_load, viewGroup, false);
      return new BottomLoadingViewHolder(bottomLoadView);
    }
  }

  @Override public int getItemViewType(int position) {
    if (!endOfFeed && position == count-1) {
      return VIEW_TYPE_BOTTOM_LOAD;
    } else {
      return VIEW_TYPE_FEED_ITEM;
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    if (viewHolder instanceof FeedItemViewHolder) {
      FeedItemViewHolder feedItemViewHolder = (FeedItemViewHolder) viewHolder;
      presenter.scrolledToIndex(position);
      FeedItem feedItem = feedItems.get(position);
      feedItemViewHolder.updateViewModel(feedItem);
    }
  }

  @Override public int getItemCount() {
    return count;
  }
}
