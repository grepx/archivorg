package gregpearce.archivorg.di;

import dagger.Component;
import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.ui.MainActivity;
import gregpearce.archivorg.ui.detail.DetailActivity;
import gregpearce.archivorg.ui.feed.FeedFragment;
import gregpearce.archivorg.ui.feed.FeedItemViewHolder;

@ActivityScope
@Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ActivityModule.class
    })
public interface ActivityComponent {
  void inject(MainActivity mainActivity);

  void inject(FeedFragment feedFragment);

  void inject(FeedItemViewHolder feedItemViewHolder);

  void inject(DetailActivity detailActivity);
}
