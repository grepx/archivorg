package gregpearce.archivorg.ui.feed;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.threeten.bp.Instant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gregpearce.archivorg.MainApplication;
import gregpearce.archivorg.R;
import gregpearce.archivorg.model.FeedItem;
import gregpearce.archivorg.model.MediaType;
import gregpearce.archivorg.ui.detail.DetailActivity;
import gregpearce.archivorg.ui.util.DateFormatter;

public class FeedItemViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.title) TextView titleTextView;
  @BindView(R.id.description) TextView descriptionTextView;
  @BindView(R.id.thumbnail) ImageView thumbnailImageView;
  @BindView(R.id.date) TextView dateTextView;

  public FeedItemViewHolder(View view) {
    super(view);
    ButterKnife.bind(this, view);
  }

  public void updateViewModel(FeedItem viewModel) {
    titleTextView.setText(viewModel.title());
    descriptionTextView.setText(viewModel.description());
    setupThumbnail(viewModel.mediaType());
    setupDate(viewModel.publishedDate());
  }

  private void setupThumbnail(MediaType mediaType) {
    Drawable drawable;
    switch (mediaType) {
      case Video:
        drawable = VectorDrawableCompat.create(MainApplication.INSTANCE.getResources(), R.drawable.ic_tv_black_24dp, null);
        break;
      case Audio:
        drawable = VectorDrawableCompat.create(MainApplication.INSTANCE.getResources(), R.drawable.ic_volume_up_black_24dp, null);
        break;
      case Book:
        drawable = VectorDrawableCompat.create(MainApplication.INSTANCE.getResources(), R.drawable.ic_chrome_reader_mode_black_24dp, null);
        break;
      case Image:
        drawable = VectorDrawableCompat.create(MainApplication.INSTANCE.getResources(), R.drawable.ic_image_black_24dp, null);
        break;
      default:
        drawable = VectorDrawableCompat.create(MainApplication.INSTANCE.getResources(), R.drawable.ic_account_balance_black_24dp, null);
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
    Intent intent = DetailActivity.getCallingIntent(dateTextView.getContext(), "abc");
    dateTextView.getContext().startActivity(intent);
  }
}