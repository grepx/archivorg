package gregpearce.archivorg.ui;

import android.support.v7.app.AppCompatActivity;

import gregpearce.archivorg.MainApplication;
import gregpearce.archivorg.di.ActivityComponent;
import gregpearce.archivorg.di.ActivityModule;
import gregpearce.archivorg.di.DaggerActivityComponent;

public abstract class BaseActivity extends AppCompatActivity {
  ActivityComponent activityComponent;

  public final ActivityComponent getComponent() {
    if (activityComponent == null) {
      activityComponent = DaggerActivityComponent.builder()
          .applicationComponent(MainApplication.APP_COMPONENT)
          .activityModule(new ActivityModule(this))
          .build();
    }
    return activityComponent;
  }
}
