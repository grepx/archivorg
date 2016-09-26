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
import gregpearce.archivorg.domain.detail.DetailPresenterFactory;
import gregpearce.archivorg.domain.detail.DetailView;
import gregpearce.archivorg.domain.detail.DetailViewState;
import gregpearce.archivorg.domain.feed.FeedViewState;
import gregpearce.archivorg.domain.model.ArchiveItem;
import gregpearce.archivorg.ui.ActivityController;
import gregpearce.archivorg.util.BundleBuilder;
import gregpearce.archivorg.util.ViewUtil;
import javax.inject.Inject;
import timber.log.Timber;

import static gregpearce.archivorg.util.ViewUtil.setVisible;

public class DetailModalController extends ActivityController implements DetailView {

  private static String ARGUMENT_ID = "ARGUMENT_ID";

  @Inject DetailPresenterFactory detailPresenterFactory;

  private DetailPresenter presenter;
  private String id;
  DetailViewState viewState;

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
    presenter = detailPresenterFactory.create(id);
  }

  @Override
  protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.controller_bottom_sheet, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    setHasOptionsMenu(true);
  }

  private void setupView() {
    updateScreen();
  }

  @Override public void update(DetailViewState updatedViewState) {
    DetailViewState oldViewState = viewState;
    viewState = updatedViewState;
    if (oldViewState.screen() != viewState.screen()) {
      updateScreen();
    }
    if (oldViewState.item() != viewState.item()) {
      updateItem();
    }
  }

  private void updateScreen() {
    setVisible(false, titleTextView, descriptionTextView, loadingProgressBar);
    switch (viewState.screen()) {
      case Detail:
        setVisible(true, titleTextView, descriptionTextView);
        break;
      case Loading:
        setVisible(true, loadingProgressBar);
        break;
      case Error:
        // todo: add error screen
        break;
      default:
        Timber.e("Unknown screen type");
    }
  }

  private void updateItem() {
    ArchiveItem item = viewState.item();
    titleTextView.setText(item.title());
    descriptionTextView.setText(item.description());
  }

  @OnTouch(R.id.modal_background)
  boolean onClickModalBackground() {
    getRouter().popController(this);
    return true;
  }

  @Override protected void onAttach(@NonNull View view) {
    viewState = presenter.subscribe(this);
    setupView();
  }

  @Override protected void onDetach(@NonNull View view) {
    presenter.unsubscribe();
  }
}
