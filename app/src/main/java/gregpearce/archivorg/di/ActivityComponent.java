package gregpearce.archivorg.di;

import dagger.Component;
import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.ui.ExploreActivity;

@ActivityScope
@Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ActivityModule.class
    })
public interface ActivityComponent {
  void inject(ExploreActivity exploreActivity);
}
