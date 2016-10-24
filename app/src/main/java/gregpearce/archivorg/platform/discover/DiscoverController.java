package gregpearce.archivorg.platform.discover;

import android.support.annotation.NonNull;
import android.view.View;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.platform.feed.FeedController;

public class DiscoverController extends BaseDiscoverController {
  @Override protected FeedController getController(FeedContentType feedContentType) {
    return FeedController.createFeedInstance(FeedType.Top, feedContentType, null);
  }

  @Override protected void onViewBound(@NonNull View view) {
    super.onViewBound(view);
    //searchView.setNavigationIconArrowHamburger();
    //searchView.setShouldClearOnOpen(false);
    //searchView.setOnMenuClickListener(() -> getDrawerLayout().openDrawer(GravityCompat.START));
  }
}
