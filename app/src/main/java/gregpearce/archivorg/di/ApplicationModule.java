package gregpearce.archivorg.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.MainApplication;

@Module
public class ApplicationModule {
  private MainApplication application;

  public ApplicationModule(MainApplication app) {
    application = app;
  }

  @Provides @Singleton MainApplication provideMainApplication() {
    return application;
  }
}