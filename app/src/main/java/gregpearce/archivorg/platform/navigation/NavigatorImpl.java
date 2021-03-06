package gregpearce.archivorg.platform.navigation;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.platform.BaseController;
import gregpearce.archivorg.platform.OverlayChildRouter;
import gregpearce.archivorg.platform.activity.MainActivity;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.platform.detail.DetailController;
import gregpearce.archivorg.platform.discover.SearchController;
import gregpearce.archivorg.platform.files.FilesController;
import javax.inject.Inject;
import timber.log.Timber;

public class NavigatorImpl implements Navigator {

  @Inject Controller controller;

  @Inject public NavigatorImpl() {
  }

  @Override public void navigateToDetail(String itemId) {
    processTransaction(RouterTransaction.with(new DetailController(itemId)));
  }

  @Override public void navigateToSearch(FeedType feedType, FeedContentType feedContentType, String query) {
    processTransaction(RouterTransaction.with(new SearchController(feedType, feedContentType, query)));
  }

  @Override public void navigateToFiles(String archiveItemId) {
    processTransaction(RouterTransaction.with(new FilesController(archiveItemId)));
  }

  private void processTransaction(RouterTransaction transaction) {
    controller.getRouter().pushController(transaction);
  }
}
