package gregpearce.archivorg.di;

import android.app.Activity;
import com.bluelinelabs.conductor.Router;
import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.domain.Navigator;
import gregpearce.archivorg.ui.navigation.NavigatorImpl;

@Module public class ControllerModule {
  private Router router;

  public ControllerModule(Router router) {
    this.router = router;
  }

  @Provides @ActivityScope Router provideRouter() {
    return router;
  }

  @Provides @ActivityScope Navigator provideNavigator(NavigatorImpl navigator) {
    return navigator;
  }
}