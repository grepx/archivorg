package gregpearce.archivorg.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.detail.DetailPresenter;
import gregpearce.archivorg.domain.detail.DetailView;
import gregpearce.archivorg.domain.model.ArchiveItem;
import gregpearce.archivorg.ui.BaseActivity;
import javax.inject.Inject;

public class DetailActivity extends BaseActivity implements DetailView {

  private static String INTENT_EXTRA_ID = "INTENT_EXTRA_ID";

  public static Intent getCallingIntent(Context context, String id) {
    Intent intent = new Intent(context, DetailActivity.class);
    intent.putExtra(INTENT_EXTRA_ID, id);
    return intent;
  }

  @Inject DetailPresenter detailPresenter;

  @BindView(R.id.title) TextView titleTextView;
  @BindView(R.id.description) TextView descriptionTextView;
  @BindView(R.id.loading_progress_bar) ProgressBar loadingProgressBar;
  @BindView(R.id.toolbar_image) ImageView toolbarImageView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_detail);
    getComponent().inject(this);
    ButterKnife.bind(this);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle("");

    detailPresenter.registerView(this);
    detailPresenter.init(getIntent().getExtras().getString(INTENT_EXTRA_ID));
  }

  @Override public void updateLoading(boolean isLoading) {
    loadingProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
  }

  @Override public void updateItem(ArchiveItem archiveItem) {
    titleTextView.setText(archiveItem.title());
    descriptionTextView.setText(archiveItem.description());
  }

  @Override public void showError() {
    Toast.makeText(this, R.string.error_network, Toast.LENGTH_LONG).show();
  }
}
