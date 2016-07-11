package gregpearce.archivorg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

import rx.Subscriber;

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
    RxView.clicks(button)
        .subscribe(new Subscriber<Void>() {

          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(Void aVoid) {
            Log.d("test", "onCreate: rxBinding working");
          }
        });
  }
}
