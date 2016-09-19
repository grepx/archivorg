package gregpearce.archivorg.di;

import dagger.Component;
import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.ui.activity.MainController;
import gregpearce.archivorg.ui.detail.DetailController;
import gregpearce.archivorg.ui.discover.DiscoverController;
import gregpearce.archivorg.ui.feed.FeedController;
import gregpearce.archivorg.ui.feed.FeedItemViewHolder;

@ActivityScope @Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ControllerModule.class
    }) public interface ControllerComponent {
  void inject(DiscoverController discoverController);

  void inject(FeedController feedController);

  void inject(FeedItemViewHolder feedItemViewHolder);

  void inject(MainController mainActivity);

  void inject(DetailController detailController);
}
