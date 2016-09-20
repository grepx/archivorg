package gregpearce.archivorg.di;

import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.MainApplication;
import gregpearce.archivorg.di.annotations.ControllerScope;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.ui.navigation.NavigatorImpl;
import javax.inject.Singleton;

@Module public class ApplicationModule {
  private MainApplication application;

  public ApplicationModule(MainApplication app) {
    application = app;
  }

  @Provides @Singleton MainApplication provideMainApplication() {
    return application;
  }

  @Provides @ControllerScope Navigator provideNavigator(NavigatorImpl navigator) {
    return navigator;
  }
}