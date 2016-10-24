package gregpearce.archivorg.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public final class RxUtil {
  private RxUtil() {
  }

  /**
   * Returns an observable that emits a single null value and never completes.
   * Can be used to bootstrap an Observable and use flatMap to run the actual start logic on
   * the subscription thread.
   */
  public static Observable<Object> bootstrap() {
    return Observable.just(null)
                     .materialize()
                     .filter(objectNotification -> !objectNotification.isOnCompleted())
                     .map(objectNotification -> null);
  }

  /**
   *  Bootstraps the stream and accepts a function to run on the subscription thread,
   *  can be used to run a Realm query on the Realm thread for instance.
   */
  public static <T extends Observable<R>, R> Observable<R> bootstrap(Func0<T> func0) {
    return bootstrap().flatMap(o -> func0.call());
  }
}
