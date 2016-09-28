package gregpearce.archivorg.ui.feed;

import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gregpearce.archivorg.MainApplication;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.feed.FeedItemPresenter;
import gregpearce.archivorg.domain.feed.FeedItemPresenterFactory;
import gregpearce.archivorg.domain.feed.FeedPresenter;
import gregpearce.archivorg.domain.model.FeedItem;
import gregpearce.archivorg.domain.model.MediaType;
import gregpearce.archivorg.ui.util.ComponentUtil;
import gregpearce.archivorg.ui.util.DateFormatter;
import javax.inject.Inject;
import org.threeten.bp.Instant;

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