package gregpearce.archivorg.di;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.MainApplication;
import gregpearce.archivorg.database.ItemRepositoryImpl;
import gregpearce.archivorg.domain.database.ItemRepository;
import io.realm.Realm;
import java.util.concurrent.Semaphore;
import javax.inject.Singleton;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module public class DatabaseModule {

  @Provides @Singleton Looper provideLooper() {
    HandlerThread bgThread = new HandlerThread("My-Background-Handler");
    bgThread.start();
    return bgThread.getLooper();
  }

  @Provides @Singleton Scheduler provideScheduler(Looper looper) {
    return AndroidSchedulers.from(looper);
  }

  @Provides @Singleton Handler provideHandler(Looper looper) {
    return new Handler(looper);
  }

  @Provides @Singleton Realm provideRealm(MainApplication context, Handler handler) {
    final Realm[] realm = new Realm[1];

    final Semaphore mutex = new Semaphore(1);
    mutex.acquireUninterruptibly();

    handler.post(() -> {
      Realm.init(context);
      realm[0] = Realm.getDefaultInstance();
      mutex.release();
    });

    mutex.acquireUninterruptibly();
    mutex.release();

    return realm[0];
  }

  @Provides @Singleton
  ItemRepository provideBookmarkRepository(ItemRepositoryImpl bookmarkRepository) {
    return bookmarkRepository;
  }
}
