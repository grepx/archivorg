package gregpearce.archivorg.platform.navigation;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.platform.BaseController;
import gregpearce.archivorg.platform.OverlayChildRouter;
import gregpearce.archivorg.platform.activity.MainActivity;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.platform.detail.DetailController;
import gregpearce.archivorg.platform.discover.SearchController;
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
          RouterTransaction.with(new DetailController(itemId)));
    } else {
      Timber.e("Activity Controller is not capable of hosting an overlay router.");
    }
  }

  @Override public void navigateToSearch(FeedContentType feedContentType, String query) {
    processTransaction(RouterTransaction.with(new SearchController(feedContentType, query)));
  }

  private void processTransaction(RouterTransaction transaction) {
    controller.getRouter().pushController(transaction);
  }
}
