package gregpearce.archivorg.ui.discover;

import android.support.annotation.NonNull;
import android.view.View;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.ui.feed.FeedController;

public class DiscoverController extends BaseDiscoverController {
  @Override protected FeedController getController(FeedContentType feedContentType) {
    return FeedController.createTopFeedInstance(feedContentType);
  }

  @Override protected void onViewBound(@NonNull View view) {
    super.onViewBound(view);
    //searchView.setNavigationIconArrowHamburger();
    //searchView.setShouldClearOnOpen(false);
    //searchView.setOnMenuClickListener(() -> getDrawerLayout().openDrawer(GravityCompat.START));
  }
}
