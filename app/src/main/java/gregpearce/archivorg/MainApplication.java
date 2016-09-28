package gregpearce.archivorg;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import gregpearce.archivorg.di.ApplicationComponent;
import gregpearce.archivorg.di.ApplicationModule;
import gregpearce.archivorg.di.DaggerApplicationComponent;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class MainApplication extends Application {

  // This is used to do dependency injection using the Application scope.
  public static ApplicationComponent APP_COMPONENT;
  public static MainApplication INSTANCE;
  private static RefWatcher refWatcher;

  @Override public void onCreate() {
    super.onCreate();

    INSTANCE = this;

    setupFabric();
    setupTimber();
    setupLeakCanary();
    AndroidThreeTen.init(this);

    ApplicationComponent component =
        DaggerApplicationComponent.builder()
                                  .applicationModule(new ApplicationModule(this))
                                  .build();
    APP_COMPONENT = component;
  }

  private void setupFabric() {
    if (!BuildConfig.DEBUG) {
      Fabric.with(this, new Crashlytics());
    }
  }

  private void setupTimber() {
    if (!BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      Timber.plant(new CrashReportingTree());
    }
  }

  private void setupLeakCanary() {
    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return;
    }
    refWatcher = LeakCanary.install(this);
  }

  public static RefWatcher getRefWatcher() {
    return refWatcher;
  }
}