package gregpearce.archivorg.platform.feed;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.feed.FeedPresenter;

public class FeedErrorViewHolder extends RecyclerView.ViewHolder {

  FeedPresenter presenter;

  public FeedErrorViewHolder(View view, FeedPresenter presenter) {
    super(view);
    this.presenter = presenter;
    ButterKnife.bind(this, view);
  }

  @OnClick(R.id.tap_to_retry_view) void onTapToRetry() {
    presenter.retry();
  }
}