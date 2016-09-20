package gregpearce.archivorg.di;

import dagger.Component;
import gregpearce.archivorg.di.annotations.ControllerScope;
import gregpearce.archivorg.ui.activity.MainActivity;
import gregpearce.archivorg.ui.detail.DetailController;
import gregpearce.archivorg.ui.discover.DiscoverController;
import gregpearce.archivorg.ui.feed.FeedController;
import gregpearce.archivorg.ui.feed.FeedItemViewHolder;

@ControllerScope @Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ControllerModule.class
    }) public interface ControllerComponent {
  void inject(DiscoverController discoverController);

  void inject(FeedController feedController);

  void inject(FeedItemViewHolder feedItemViewHolder);

  void inject(MainActivity mainActivity);

  void inject(DetailController detailController);
}
