package gregpearce.archivorg.di;

import android.content.res.Resources;
import com.bluelinelabs.conductor.Controller;
import dagger.Module;
import dagger.Provides;

@Module public class ViewModule {
  @Provides Resources provideResources(Controller controller) {
    return controller.getResources();
  }
}
