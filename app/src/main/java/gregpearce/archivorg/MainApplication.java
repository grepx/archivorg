package gregpearce.archivorg;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.jakewharton.threetenabp.AndroidThreeTen;
import gregpearce.archivorg.di.ApplicationComponent;
import gregpearce.archivorg.di.ApplicationModule;
import gregpearce.archivorg.di.DaggerApplicationComponent;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class MainApplication extends Application {

  // This is used to do dependency injection using the Application scope.
  public static ApplicationComponent APP_COMPONENT;
  public static MainApplication INSTANCE;

  @Override public void onCreate() {
    super.onCreate();
    Fabric.with(this, new Crashlytics());
    Timber.plant(new Timber.DebugTree());
    INSTANCE = this;

    AndroidThreeTen.init(this);

    ApplicationComponent component =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    APP_COMPONENT = component;
  }
}