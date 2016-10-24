package gregpearce.archivorg.data.database.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import io.realm.Realm;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import timber.log.Timber;

public class RealmUtil {

  private static Looper looper;

  private static Scheduler scheduler;

  private static Handler handler;

  private static Realm realm;

  private static Looper getLooper() {
    if (looper == null) {
      HandlerThread thread = new HandlerThread("Realm-Handler-Thread");
      thread.start();
      looper = thread.getLooper();
    }
    return looper;
  }

  /**
   *  Used to access the Realm instance singleton.
   */
  public static Realm getRealm() {
    if (Thread.currentThread() != RealmUtil.getLooper().getThread()) {
      throw new RuntimeException("Not currently on the Realm thread.");
    }
    if (realm == null) {
      realm = Realm.getDefaultInstance();
    }
    return realm;
  }

  /**
   * Handler for posting to the Realm thread.
   */
  private static Handler getHandler() {
    if (handler == null) {
      handler = new Handler(getLooper());
    }
    return handler;
  }

  /**
   *  Helper method for using Realm on the correct thread.
   */
  public static void doRealmTransaction(Action1<Realm> action) {
    getHandler().post(() -> {
      Realm realm = getRealm();
      realm.beginTransaction();
      action.call(realm);
      realm.commitTransaction();
    });
  }

  /**
   * The scheduler used by rxJava.
   */
  private static Scheduler getScheduler() {
    if (scheduler == null) {
      scheduler = AndroidSchedulers.from(getLooper());
    }
    return scheduler;
  }

  /**
   *  rxJava subscription defaults.
   */
  public static <T> Observable.Transformer<T, T> subscribeDefaults() {
    return observable -> observable.doOnError(
        error -> Timber.e(error, "Realm Error Handler: %s", error.getMessage()))
                                   .subscribeOn(getScheduler())
                                   .unsubscribeOn(getScheduler());
  }
}
