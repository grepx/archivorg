package gregpearce.archivorg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

  @Inject
  MainApplication mainApplication;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MainApplication.APP_COMPONENT.inject(this);

    setContentView(R.layout.activity_main);

    // test retrolambda works
    Button button = (Button) findViewById(R.id.button);
    button.setOnClickListener(view -> {
      Log.d("test", "onCreate: retrolambda working");
    });
  }
}
