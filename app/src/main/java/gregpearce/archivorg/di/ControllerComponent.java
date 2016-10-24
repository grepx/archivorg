package gregpearce.archivorg.di;

import dagger.Component;
import gregpearce.archivorg.di.annotations.ControllerScope;
import gregpearce.archivorg.platform.activity.MainActivity;
import gregpearce.archivorg.platform.bookmarks.BookmarkController;
import gregpearce.archivorg.platform.detail.DetailController;
import gregpearce.archivorg.platform.discover.BaseDiscoverController;
import gregpearce.archivorg.platform.discover.SearchController;
import gregpearce.archivorg.platform.feed.FeedController;
import gregpearce.archivorg.platform.feed.FeedItemViewHolder;

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
