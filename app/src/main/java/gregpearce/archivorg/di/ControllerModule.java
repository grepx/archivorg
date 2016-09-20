package gregpearce.archivorg.di;

import android.app.Activity;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.ui.navigation.NavigatorImpl;

@Module public class ControllerModule {
  private Controller controller;

  public ControllerModule(Controller controller) {
    this.controller = controller;
  }

  @Provides @ActivityScope Controller provideController() {
    return controller;
  }

  @Provides @ActivityScope Navigator provideNavigator(NavigatorImpl navigator) {
    return navigator;
  }
}