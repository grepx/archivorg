package gregpearce.archivorg.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public final class RxUtil {
  private RxUtil() {
  }

  /**
   *  Returns an observable that emits a single null value and never completes.
   *  Can be used to bootstrap an Observable and use flatMap to run the actual start logic on
   *  the subscription thread (used for Realm queries).
   */
  public static Observable<Object> bootstrap() {
    return Observable.just(null)
                     .materialize()
                     .filter(objectNotification -> !objectNotification.isOnCompleted())
                     .map(objectNotification -> null);
  }

  public static <T> Observable.Transformer<T, T> viewDefaults() {
    return observable -> observable.observeOn(Schedulers.io())
                                   .subscribeOn(AndroidSchedulers.mainThread());
  }

  public static <T> Observable.Transformer<T, T> subscribeDefaults() {
    return observable -> observable.doOnError(
        error -> Timber.e(error, "Default Error Handler: %s", error.getMessage()))
                                   .subscribeOn(Schedulers.io())
                                   .observeOn(AndroidSchedulers.mainThread());
  }
}
