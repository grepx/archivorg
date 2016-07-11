package gregpearce.archivorg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

  @Inject
  MainApplication mainApplication;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MainApplication.APP_COMPONENT.inject(this);

    setContentView(R.layout.activity_main);

    // test injection worked
    if (mainApplication != null) {
      Log.d("test", "onCreate: injection worked");
    }
  }
}
