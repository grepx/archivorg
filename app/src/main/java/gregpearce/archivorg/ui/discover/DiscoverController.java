package gregpearce.archivorg.ui.discover;

import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.view.View;
import gregpearce.archivorg.ui.feed.FeedController;
import gregpearce.archivorg.domain.model.FeedType;

public class DiscoverController extends BaseDiscoverController {
  @Override protected FeedController getController(FeedType feedType) {
    return FeedController.createTopFeedInstance(feedType);
  }

  @Override protected void onViewBound(@NonNull View view) {
    super.onViewBound(view);
    searchView.setNavigationIconArrowHamburger();
    searchView.setShouldClearOnOpen(false);
    searchView.setOnMenuClickListener(() -> getDrawerLayout().openDrawer(GravityCompat.START));
  }
}
