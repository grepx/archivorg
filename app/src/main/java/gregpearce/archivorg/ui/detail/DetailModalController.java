package gregpearce.archivorg.ui.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.detail.DetailPresenter;
import gregpearce.archivorg.domain.detail.DetailView;
import gregpearce.archivorg.domain.model.ArchiveItem;
import gregpearce.archivorg.ui.ActivityController;
import gregpearce.archivorg.util.BundleBuilder;
import gregpearce.archivorg.util.ViewUtil;
import javax.inject.Inject;

public class DetailModalController extends ActivityController implements DetailView {

  private static String ARGUMENT_ID = "ARGUMENT_ID";

  @Inject DetailPresenter detailPresenter;

  private String id;

  @BindView(R.id.title) TextView titleTextView;
  @BindView(R.id.description) TextView descriptionTextView;
  @BindView(R.id.loading_progress_bar) View  loadingProgressBar;
  //@BindView(R.id.toolbar_image) ImageView toolbarImageView;

  public DetailModalController(String id) {
    this(BundleBuilder.create().putString(ARGUMENT_ID, id).build());
  }

  public DetailModalController(Bundle args) {
    super(args);
    id = args.getString(ARGUMENT_ID);
  }

  @Override protected void onCreate() {
    super.onCreate();
    getComponent().inject(this);
  }

  @Override
  protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.controller_bottom_sheet, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    setHasOptionsMenu(true);

    detailPresenter.registerView(this);
    detailPresenter.init(id);
  }

  @Override public void updateLoading(boolean isLoading) {
    ViewUtil.setVisible(loadingProgressBar, isLoading);
  }

  @Override public void updateItem(ArchiveItem archiveItem) {
    titleTextView.setText(archiveItem.title());
    descriptionTextView.setText(archiveItem.description());
  }

  @Override public void showError() {
    Toast.makeText(getActivity(), R.string.error_network, Toast.LENGTH_LONG).show();
  }

  @Override public void update(Object updatedViewState) {

  }

  @OnTouch(R.id.modal_background)
  boolean onClickModalBackground() {
    getRouter().popController(this);
    return true;
  }
}
