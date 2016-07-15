package gregpearce.archivorg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

import gregpearce.archivorg.model.ArchiveEntity;
import gregpearce.archivorg.network.SearchService;
import gregpearce.archivorg.util.RxUtil;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

  @Inject SearchService searchService;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MainApplication.APP_COMPONENT.inject(this);

    setContentView(R.layout.activity_main);

    EditText editTextQuery = (EditText) findViewById(R.id.query);

    Button button = (Button) findViewById(R.id.button);
    RxView.clicks(button)
        .compose(RxUtil.viewDefaults())
        .flatMap(click -> searchService.search(editTextQuery.getText().toString(), 1))
        .compose(RxUtil.subscriberDefaults())
        .subscribe(
            result -> {
              Timber.d("number of search results: %d", result.getTotalCount());
              for (ArchiveEntity archiveEntity : result.getResults()) {
                Timber.d("entity title: %s", archiveEntity.getTitle());
              }
            },
            error -> {
            });
  }
}
