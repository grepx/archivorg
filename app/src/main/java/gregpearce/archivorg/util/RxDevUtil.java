package gregpearce.archivorg.util;

import java.util.Random;
import rx.Observable;

public class RxDevUtil {
  /**
   *  Makes a stream throw an exception, useful for development.
   */
  public static <T> Observable.Transformer<T, T> throwException() {
    return observable -> observable.map(t -> {
      if (true) {
        throw new RuntimeException("Runtime Exception deliberately inserted into stream.");
      }
      return t;
    });
  }

  /**
   *  Makes a stream throw an exception 50% of the time, useful for development.
   */
  public static <T> Observable.Transformer<T, T> throwExceptionSometimes() {
    return observable -> observable.map(t -> {
      if (new Random(System.currentTimeMillis()).nextBoolean()) {
        throw new RuntimeException("Runtime Exception deliberately inserted into stream.");
      }
      return t;
    });
  }
}
