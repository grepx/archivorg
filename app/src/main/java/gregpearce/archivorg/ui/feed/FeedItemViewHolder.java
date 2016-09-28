package gregpearce.archivorg.ui.feed;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gregpearce.archivorg.R;
import gregpearce.archivorg.di.ControllerComponent;
import gregpearce.archivorg.domain.feed.FeedItemPresenter;
import gregpearce.archivorg.domain.feed.FeedItemPresenterFactory;
import gregpearce.archivorg.domain.model.FeedItem;
import gregpearce.archivorg.domain.model.MediaType;
import gregpearce.archivorg.ui.util.DateFormatter;
import javax.inject.Inject;
import org.threeten.bp.Instant;

public class FeedItemViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.title) TextView titleTextView;
  @BindView(R.id.description) TextView descriptionTextView;
  @BindView(R.id.thumbnail) ImageView thumbnailImageView;
  @BindView(R.id.date) TextView dateTextView;

  @Inject FeedItemPresenterFactory feedItemPresenterFactory;
  @Inject Resources resources;

  FeedItemPresenter presenter;

  public FeedItemViewHolder(ControllerComponent component, View view) {
    super(view);
    ButterKnife.bind(this, view);
    component.inject(this);
  }

  public void update(FeedItem feedItem) {
    titleTextView.setText(feedItem.title());
    descriptionTextView.setText(feedItem.description());
    setupThumbnail(feedItem.mediaType());
    setupDate(feedItem.publishedDate());

    presenter = feedItemPresenterFactory.create(feedItem.id());
  }

  private void setupThumbnail(MediaType mediaType) {
    Drawable drawable;
    switch (mediaType) {
      case Video:
        drawable = VectorDrawableCompat.create(resources, R.drawable.ic_tv_black_24dp, null);
        break;
      case Audio:
        drawable = VectorDrawableCompat.create(resources, R.drawable.ic_volume_up_black_24dp, null);
        break;
      case Book:
        drawable = VectorDrawableCompat.create(resources,
                                               R.drawable.ic_chrome_reader_mode_black_24dp,
                                               null);
        break;
      case Image:
        drawable = VectorDrawableCompat.create(resources, R.drawable.ic_image_black_24dp, null);
        break;
      default:
        drawable =
            VectorDrawableCompat.create(resources, R.drawable.ic_account_balance_black_24dp, null);
    }
    thumbnailImageView.setImageDrawable(drawable);
  }

  private void setupDate(Instant date) {
    if (date == null) {
      dateTextView.setText("Published Date Unknown");
    } else {
      dateTextView.setText(DateFormatter.formatDate(date));
    }
  }

  @OnClick(R.id.card_view) void onClick() {
    presenter.click();
  }
}