package gregpearce.archivorg.di;

import com.bluelinelabs.conductor.Controller;
import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.di.annotations.ControllerScope;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.ui.navigation.NavigatorImpl;

@Module public class ControllerModule {
  private Controller controller;

  public ControllerModule(Controller controller) {
    this.controller = controller;
  }

  @Provides @ControllerScope Controller provideController() {
    return controller;
  }

  @Provides @ControllerScope Navigator provideNavigator(NavigatorImpl navigator) {
    return navigator;
  }
}