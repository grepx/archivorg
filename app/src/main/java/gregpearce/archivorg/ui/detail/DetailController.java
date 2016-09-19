package gregpearce.archivorg.ui.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.detail.DetailPresenter;
import gregpearce.archivorg.domain.detail.DetailView;
import gregpearce.archivorg.domain.model.ArchiveItem;
import gregpearce.archivorg.ui.BaseController;
import gregpearce.archivorg.util.BundleBuilder;
import javax.inject.Inject;

public class DetailController extends BaseController implements DetailView {

  private static String ARGUMENT_ID = "ARGUMENT_ID";

  @Inject DetailPresenter detailPresenter;

  private String id;

  @BindView(R.id.title) TextView titleTextView;
  @BindView(R.id.description) TextView descriptionTextView;
  @BindView(R.id.loading_progress_bar) ProgressBar loadingProgressBar;
  //@BindView(R.id.toolbar_image) ImageView toolbarImageView;

  public DetailController(String id) {
    this(new BundleBuilder().putString(ARGUMENT_ID, id).build());
  }

  public DetailController(Bundle args) {
    super(args);
    id = args.getString(ARGUMENT_ID);
  }

  @Override protected void onCreate() {
    super.onCreate();
    getComponent().inject(this);
  }

  @Override
  protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.activity_detail, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    detailPresenter.registerView(this);
    detailPresenter.init(id);
  }

  @Override public void updateLoading(boolean isLoading) {
    loadingProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
  }

  @Override public void updateItem(ArchiveItem archiveItem) {
    titleTextView.setText(archiveItem.title());
    descriptionTextView.setText(archiveItem.description());
  }

  @Override public void showError() {
    Toast.makeText(getActivity(), R.string.error_network, Toast.LENGTH_LONG).show();
  }
}
