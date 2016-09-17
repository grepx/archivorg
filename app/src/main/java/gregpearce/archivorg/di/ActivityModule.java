package gregpearce.archivorg.di;

import android.app.Activity;
import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.ui.BaseActivity;
import gregpearce.archivorg.ui.navigation.NavigatorImpl;

@Module public class ActivityModule {
  private Activity activity;

  public ActivityModule(Activity activity) {
    this.activity = activity;
  }

  @Provides @ActivityScope Activity provideActivity() {
    return activity;
  }

  @Provides @ActivityScope Navigator provideNavigator(NavigatorImpl navigator) {
    return navigator;
  }
}