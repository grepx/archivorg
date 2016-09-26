package gregpearce.archivorg.ui.navigation;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.ui.BaseController;
import gregpearce.archivorg.ui.OverlayChildRouter;
import gregpearce.archivorg.ui.activity.MainActivity;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.ui.detail.DetailModalController;
import gregpearce.archivorg.ui.discover.SearchController;
import javax.inject.Inject;
import timber.log.Timber;

public class NavigatorImpl implements Navigator {

  @Inject Controller controller;

  @Inject public NavigatorImpl() {
  }

  @Override public void navigateToDetailBottomSheet(String itemId) {
    BaseController activityController =
        ((MainActivity) this.controller.getActivity()).getActivityController();
    if (activityController instanceof OverlayChildRouter) {
      ((OverlayChildRouter) activityController).pushOverlayController(
          RouterTransaction.with(new DetailModalController(itemId)));
    } else {
      Timber.e("Activity Controller is not capable of hosting an overlay router.");
    }
  }

  @Override public void navigateToSearch(FeedType feedType, String query) {
    processTransaction(RouterTransaction.with(new SearchController(feedType, query)));
  }

  private void processTransaction(RouterTransaction transaction) {
    controller.getRouter().pushController(transaction);
  }
}
