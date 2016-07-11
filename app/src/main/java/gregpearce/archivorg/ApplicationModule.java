package gregpearce.archivorg;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
  private MainApplication mApplication;

  public ApplicationModule(MainApplication app) {
    mApplication = app;
  }

  @Provides @Singleton MainApplication provideMainApplication() {
    return mApplication;
  }
}