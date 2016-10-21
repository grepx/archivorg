package gregpearce.archivorg.di;

import dagger.Component;
import gregpearce.archivorg.di.annotations.ControllerScope;
import gregpearce.archivorg.ui.activity.MainActivity;
import gregpearce.archivorg.ui.bookmarks.BookmarkController;
import gregpearce.archivorg.ui.detail.DetailController;
import gregpearce.archivorg.ui.discover.BaseDiscoverController;
import gregpearce.archivorg.ui.discover.SearchController;
import gregpearce.archivorg.ui.feed.FeedController;
import gregpearce.archivorg.ui.feed.FeedItemViewHolder;

@ControllerScope @Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ControllerModule.class,
        PlatformModule.class,
        ViewModule.class,
    }) public interface ControllerComponent {
  void inject(BaseDiscoverController baseDiscoverController);

  void inject(FeedController feedController);

  void inject(FeedItemViewHolder feedItemViewHolder);

  void inject(MainActivity mainActivity);

  void inject(DetailController detailController);

  void inject(SearchController searchController);

  void inject(BookmarkController bookmarkController);
}
