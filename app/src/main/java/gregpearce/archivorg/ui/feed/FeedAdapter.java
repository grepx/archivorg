package gregpearce.archivorg.ui.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gregpearce.archivorg.R;

public class FeedAdapter extends RecyclerView.Adapter<FeedItemViewHolder> {

  private FeedPresenter presenter;
  private List<FeedItem> feedItems = new ArrayList<>(0);

  public FeedAdapter(FeedPresenter presenter) {
    this.presenter = presenter;
  }

  void updateFeed(List<FeedItem> feedItems, boolean endOfFeed) {
    this.feedItems = feedItems;
    notifyDataSetChanged();
  }

  @Override public FeedItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_item, viewGroup, false);
    return new FeedItemViewHolder(view);
  }

  @Override public void onBindViewHolder(FeedItemViewHolder viewHolder, int position) {
    presenter.scrolledToIndex(position);
    FeedItem feedItem = feedItems.get(position);
    viewHolder.updateViewModel(feedItem);
  }

  @Override public int getItemCount() {
    return feedItems.size();
  }
}
