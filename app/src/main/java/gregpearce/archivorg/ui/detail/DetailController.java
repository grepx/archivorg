package gregpearce.archivorg.ui.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnTouch;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.detail.DetailPresenter;
import gregpearce.archivorg.domain.detail.DetailPresenterFactory;
import gregpearce.archivorg.domain.detail.DetailView;
import gregpearce.archivorg.domain.detail.DetailViewState;
import gregpearce.archivorg.domain.model.ArchiveItem;
import gregpearce.archivorg.ui.BaseController;
import gregpearce.archivorg.util.BundleBuilder;
import javax.inject.Inject;
import timber.log.Timber;

import static gregpearce.archivorg.util.ViewUtil.setVisible;

public class DetailController extends BaseController implements DetailView {

  private static String ARGUMENT_ID = "ARGUMENT_ID";

  @Inject DetailPresenterFactory detailPresenterFactory;

  private DetailPresenter presenter;
  private String id;
  private DetailViewState viewState;

  @BindView(R.id.bottom_sheet_layout) View bottomSheetLayout;
  @BindView(R.id.title) TextView titleTextView;
  @BindView(R.id.description) TextView descriptionTextView;
  @BindView(R.id.loading_progress_bar) View  loadingProgressBar;

  public DetailController(String id) {
    this(BundleBuilder.create().putString(ARGUMENT_ID, id).build());
  }

  public DetailController(Bundle args) {
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
    return inflater.inflate(R.layout.controller_detail, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    setHasOptionsMenu(true);

    BottomSheetBehavior.from(bottomSheetLayout).setBottomSheetCallback(
        new BottomSheetBehavior.BottomSheetCallback() {
          @Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
              finish();
            }
          }

          @Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {
          }
        });
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
    finish();
    return true;
  }

  @Override protected void onAttach(@NonNull View view) {
    getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    viewState = presenter.subscribe(this);
    setupView();
  }

  @Override protected void onDetach(@NonNull View view) {
    super.onDetach(view);
    // Manually set the drawer state to what it should be after this Controller terminates.
    // Don't know a clean way to do this. Would need a onResume method on BaseDiscoverController.
    getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);

    presenter.unsubscribe();
  }
}