package gregpearce.archivorg.di;

import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.ui.BaseActivity;

@Module
public class ActivityModule {
  private BaseActivity activity;

  public ActivityModule(BaseActivity activity) {
    this.activity = activity;
  }

  @Provides @ActivityScope BaseActivity provideActivity() {
    return activity;
  }
}