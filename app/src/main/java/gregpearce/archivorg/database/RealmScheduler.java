package gregpearce.archivorg.database;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class RealmScheduler {
  private static Scheduler scheduler = Schedulers.from(RealmExecutor.executor);

  public static Scheduler getScheduler() {
    return scheduler;
  }
}
