package gregpearce.archivorg.database;

import android.os.Handler;
import gregpearce.archivorg.database.model.ArchiveItemRecord;
import gregpearce.archivorg.domain.database.ItemRepository;
import gregpearce.archivorg.domain.model.ArchiveItem;
import io.realm.Realm;
import io.realm.RealmQuery;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import rx.Notification;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ItemRepositoryImpl implements ItemRepository {

  @Inject Realm realm;
  @Inject Scheduler scheduler;
  @Inject Handler handler;

  @Inject public ItemRepositoryImpl() {
  }

  private RealmQuery<ArchiveItemRecord> getRecords() {
    return realm.where(ArchiveItemRecord.class);
  }

  @Override public Observable<List<ArchiveItem>> getBookmarkedItems() {
    return getRecords().equalTo("isBookmarked", true).findAll().asObservable()
                       .map((records) -> ArchiveItemRecord.mapToDomainList(records))
                       .subscribeOn(scheduler);
  }

  @Override public Observable<ArchiveItem> get(String id) {
    return Observable.just(null)
                     .materialize()
                     .filter(objectNotification -> !objectNotification.isOnCompleted())
                     .flatMap(nil ->
                                  realm.where(ArchiveItemRecord.class)
                                       .equalTo("id", id).findAll().asObservable()
                             )
                     .map((rs) -> {
                       if (rs.size() == 0) {
                         return null;
                       } else {
                         return ArchiveItemRecord.mapToDomain(rs.first());
                       }
                     })
                     .subscribeOn(scheduler)
                     .unsubscribeOn(scheduler);
  }

  @Override public void put(ArchiveItem feedItem) {
    handler.post(() -> {
      ArchiveItemRecord record = ArchiveItemRecord.mapToRecord(feedItem);
      realm.beginTransaction();
      realm.insertOrUpdate(record);
      realm.commitTransaction();
    });
  }
}
