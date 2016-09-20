package gregpearce.archivorg.ui.navigation;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.ui.detail.DetailController;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

  private void processTransaction(RouterTransaction transaction) {
    controller.getRouter().pushController(transaction);
  }
}
