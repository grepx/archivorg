package gregpearce.archivorg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

  @Inject MainApplication mainApplication;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MainApplication.APP_COMPONENT.inject(this);

    setContentView(R.layout.activity_main);

    Button button = (Button) findViewById(R.id.button);
    RxView.clicks(button)
        .map(click -> 1)
        .scan((count, click) -> Integer.valueOf(count + 1))
        .subscribe(count -> {
          Log.d("test", "call: number of clicks: " + count);
        });
  }
}
