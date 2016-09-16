package gregpearce.archivorg.di;

import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.MainApplication;
import javax.inject.Singleton;

@Module public class ApplicationModule {
  private MainApplication application;

  public ApplicationModule(MainApplication app) {
    application = app;
  }

  @Provides @Singleton MainApplication provideMainApplication() {
    return application;
  }
}