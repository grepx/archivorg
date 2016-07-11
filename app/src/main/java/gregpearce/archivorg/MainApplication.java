package gregpearce.archivorg;

import android.app.Application;

public class MainApplication extends Application {

  // This is used to do dependency injection using the Application scope.
  public static ApplicationComponent APP_COMPONENT;

  @Override public void onCreate() {
    super.onCreate();
    ApplicationComponent component = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
    APP_COMPONENT = component;
  }
}