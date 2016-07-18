package gregpearce.archivorg.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gregpearce.archivorg.R;

public class FeedAdapter extends RecyclerView.Adapter<FeedItemViewHolder> {

  public FeedAdapter() {
  }

  @Override public FeedItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_item, viewGroup, false);
    return new FeedItemViewHolder(view);
  }

  @Override public void onBindViewHolder(FeedItemViewHolder viewHolder, int position) {
    FeedItemViewModel viewModel = FeedItemViewModel.create("title " + position, "description " + position);
    viewHolder.updateViewModel(viewModel);
  }

  @Override public int getItemCount() {
    return 20;
  }
}
