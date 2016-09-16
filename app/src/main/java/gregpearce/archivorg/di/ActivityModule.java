package gregpearce.archivorg.di;

import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.ui.BaseActivity;
import gregpearce.archivorg.ui.navigation.NavigatorImpl;

@Module public class ActivityModule {
  private BaseActivity activity;

  public ActivityModule(BaseActivity activity) {
    this.activity = activity;
  }

  @Provides @ActivityScope BaseActivity provideActivity() {
    return activity;
  }

  @Provides @ActivityScope Navigator provideNavigator(NavigatorImpl navigator) {
    return navigator;
  }
}