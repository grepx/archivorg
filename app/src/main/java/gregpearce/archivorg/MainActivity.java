package gregpearce.archivorg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

import gregpearce.archivorg.model.ArchiveEntity;
import gregpearce.archivorg.model.PagedResult;
import gregpearce.archivorg.network.SearchService;
import rx.Subscriber;
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
        .flatMap(click -> searchService.search(editTextQuery.getText().toString(), 1))
        .subscribe(new Subscriber<PagedResult>() {
          @Override public void onCompleted() {
          }

          @Override public void onError(Throwable e) {
            Timber.e(e, e.getMessage());
          }

          @Override public void onNext(PagedResult pagedResult) {
            Timber.d("number of search results: %d", pagedResult.getTotalCount());
            for (ArchiveEntity archiveEntity : pagedResult.getResults()) {
              Timber.d("entity title: %s", archiveEntity.getTitle());
            }
          }
        });
  }
}
