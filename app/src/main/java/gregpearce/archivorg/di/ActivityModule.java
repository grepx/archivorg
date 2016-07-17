package gregpearce.archivorg.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.ui.BaseActivity;

@Module
public class ActivityModule {
  private BaseActivity activity;

  public ActivityModule(BaseActivity activity) {
    this.activity = activity;
  }

  @Provides @Singleton BaseActivity provideActivity() {
    return activity;
  }
}
