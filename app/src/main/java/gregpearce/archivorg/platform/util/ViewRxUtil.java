package gregpearce.archivorg.platform.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ViewRxUtil {
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
