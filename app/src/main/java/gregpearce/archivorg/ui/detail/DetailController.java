package gregpearce.archivorg.ui.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.detail.DetailPresenter;
import gregpearce.archivorg.domain.detail.DetailPresenterFactory;
import gregpearce.archivorg.domain.detail.DetailView;
import gregpearce.archivorg.domain.detail.DetailViewState;
import gregpearce.archivorg.domain.model.ArchiveItem;
import gregpearce.archivorg.ui.BaseController;
import gregpearce.archivorg.ui.PresenterController;
import gregpearce.archivorg.ui.widget.ProfileButton;
import gregpearce.archivorg.util.BundleBuilder;
import javax.inject.Inject;
import timber.log.Timber;

import static gregpearce.archivorg.util.ViewUtil.setVisible;

public class DetailController extends PresenterController implements DetailView {

  private static String ARGUMENT_ID = "ARGUMENT_ID";

  @Inject DetailPresenterFactory detailPresenterFactory;

  private DetailPresenter presenter;
  private String id;
  private DetailViewState viewState;

  @BindView(R.id.bottom_sheet_layout) View bottomSheetLayout;
  @BindView(R.id.tap_to_retry_view) TextView tapToRetryView;
  @BindView(R.id.details_view) View detailsView;
  @BindView(R.id.title) TextView titleView;
  @BindView(R.id.description) WebView descriptionView;
  @BindView(R.id.loading_view) View loadingView;
  @BindView(R.id.button_bookmark) BookmarkButton bookmarkButton;

  public DetailController(String id) {
    this(BundleBuilder.create().putString(ARGUMENT_ID, id).build());
  }

  public DetailController(Bundle args) {
    super(args);
    id = args.getString(ARGUMENT_ID);
  }

  @Override protected void onCreate() {
    getComponent().inject(this);
    presenter = detailPresenterFactory.create(id);
    registerPresenter(presenter);
  }

  @Override
  protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.controller_detail, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    setHasOptionsMenu(true);
    descriptionView.getSettings().setJavaScriptEnabled(true);

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
    setVisible(false, loadingView, detailsView, tapToRetryView);
    switch (viewState.screen()) {
      case Detail:
        setVisible(true, detailsView);
        break;
      case Loading:
        setVisible(true, loadingView);
        break;
      case Error:
        setVisible(true, tapToRetryView);
        break;
      default:
        Timber.e("Unknown screen type");
    }
  }

  private void updateItem() {
    ArchiveItem item = viewState.item();
    titleView.setText(item.title());
    descriptionView.loadDataWithBaseURL("", item.description(), "text/html", "UTF-8", "");
    bookmarkButton.setState(viewState.item().isBookmarked() ?
                           BookmarkButton.STATE_BOOKMARKED :
                           BookmarkButton.STATE_NOT_BOOKMARKED);
  }

  @OnTouch(R.id.modal_background)
  boolean onTouchModalBackground() {
    finish();
    return true;
  }

  @OnClick(R.id.tap_to_retry_view)
  void onTapToRetry() {
    presenter.refresh();
  }

  @OnClick(R.id.button_share)
  void onShare() {
    presenter.share();
  }

  @OnClick(R.id.button_bookmark)
  void onBookmark() {
    presenter.bookmark();
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