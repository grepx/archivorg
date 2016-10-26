package gregpearce.archivorg.platform.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.detail.DetailPresenter;
import gregpearce.archivorg.domain.detail.DetailPresenterFactory;
import gregpearce.archivorg.domain.detail.DetailView;
import gregpearce.archivorg.domain.detail.DetailViewState;
import gregpearce.archivorg.domain.model.ArchiveItem;
import gregpearce.archivorg.platform.PresenterController;
import gregpearce.archivorg.platform.util.BundleBuilder;
import javax.inject.Inject;
import timber.log.Timber;

import static gregpearce.archivorg.platform.util.ViewUtil.setVisible;

public class DetailController extends PresenterController implements DetailView {

  private static String ARGUMENT_ID = "ARGUMENT_ID";

  @Inject DetailPresenterFactory detailPresenterFactory;

  private DetailPresenter presenter;
  private String id;
  private DetailViewState viewState;

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.tap_to_retry_view) TextView tapToRetryView;
  @BindView(R.id.details_view) View detailsView;
  @BindView(R.id.title) TextView titleView;
  @BindView(R.id.description) TextView descriptionView;
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
    setActionBar(toolbar);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setTitle("");
  }

  private void setupView() {
    updateScreen();
    if (viewState.item() != null) {
      updateItem();
    }
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
    descriptionView.setText(item.description());
    bookmarkButton.setState(viewState.item().bookmarkedDate() != null ?
                           BookmarkButton.STATE_BOOKMARKED :
                           BookmarkButton.STATE_NOT_BOOKMARKED);
    getActionBar().setTitle(item.title());
  }

  @OnClick(R.id.tap_to_retry_view)
  void onTapToRetry() {
    presenter.refresh();
  }

  @OnClick(R.id.button_share)
  void onClickShare() {
    presenter.share();
  }

  @OnClick(R.id.button_bookmark)
  void onClickBookmark() {
    presenter.bookmark();
  }

  @OnClick(R.id.button_files)
  void onClickFiles() {
    presenter.files();
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