package gregpearce.archivorg.ui.navigation;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.ui.activity.MainActivity;
import gregpearce.archivorg.ui.detail.DetailController;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.ui.detail.DetailModalController;
import gregpearce.archivorg.ui.discover.SearchController;
import javax.inject.Inject;

public class NavigatorImpl implements Navigator {

  @Inject Controller controller;

  @Inject public NavigatorImpl() {
  }

  @Override public void navigateToDiscover() {
  }

  @Override public void navigateToDetail(String itemId) {
    processTransaction(RouterTransaction.with(new DetailController(itemId)));
  }

  @Override public void navigateToDetailBottomSheet(String itemId) {
    ((MainActivity) controller.getActivity())
        .pushModalController(RouterTransaction.with(new DetailModalController(itemId)));
  }

  @Override public void navigateToSearch(FeedType feedType, String query) {
    processTransaction(RouterTransaction.with(new SearchController(feedType, query)));
  }

  private void processTransaction(RouterTransaction transaction) {
    controller.getRouter().pushController(transaction);
  }
}
