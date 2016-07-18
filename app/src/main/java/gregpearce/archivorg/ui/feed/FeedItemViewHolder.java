package gregpearce.archivorg.ui.feed;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import gregpearce.archivorg.R;

public class FeedItemViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.title) TextView titleTextView;
  @BindView(R.id.description) TextView descriptionTextView;

  public FeedItemViewHolder(View view) {
    super(view);
    ButterKnife.bind(this, view);
  }

  public void updateViewModel(FeedItemModel viewModel) {
    titleTextView.setText(viewModel.title());
    descriptionTextView.setText(viewModel.description());
  }
}