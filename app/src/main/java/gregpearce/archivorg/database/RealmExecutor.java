package gregpearce.archivorg.database;

import gregpearce.archivorg.MainApplication;
import io.realm.Realm;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.functions.Action1;

@Singleton
public class RealmExecutor {
  static Executor executor = Executors.newSingleThreadExecutor();
  private Realm realm;

  private Semaphore mutex = new Semaphore(1);

  @Inject public RealmExecutor(MainApplication context) {
    executor.execute(() -> {
      Realm.init(context);
      realm = Realm.getDefaultInstance();
    });
  }

  public void execute(Action1<Realm> transaction) {
    executor.execute(() -> transaction.call(realm));
  }


}
