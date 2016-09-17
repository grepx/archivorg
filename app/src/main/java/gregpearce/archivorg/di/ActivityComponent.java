package gregpearce.archivorg.di;

import dagger.Component;
import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.ui.detail.MainActivity;
import gregpearce.archivorg.ui.detail.DetailController;
import gregpearce.archivorg.ui.discover.DiscoverActivity;
import gregpearce.archivorg.ui.feed.FeedFragment;
import gregpearce.archivorg.ui.feed.FeedItemViewHolder;

@ActivityScope @Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ActivityModule.class
    }) public interface ActivityComponent {
  void inject(DiscoverActivity discoverActivity);

  void inject(FeedFragment feedFragment);

  void inject(FeedItemViewHolder feedItemViewHolder);

  void inject(MainActivity mainActivity);

  void inject(DetailController detailController);
}
