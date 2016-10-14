package gregpearce.archivorg.database;

import gregpearce.archivorg.database.model.ArchiveItemRecord;
import gregpearce.archivorg.domain.database.ItemRepository;
import gregpearce.archivorg.domain.model.ArchiveItem;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;

public class BookmarkRepositoryImpl implements ItemRepository {

  private BehaviorSubject<List<ArchiveItem>> subject =
      BehaviorSubject.create(Collections.EMPTY_LIST);
  private Realm realm;

  @Inject public BookmarkRepositoryImpl(Realm realm) {
    this.realm = realm;
  }

  @Override public Observable<List<ArchiveItem>> getBookmarkedItems() {
    return subject.asObservable();
  }

  @Override public Observable<ArchiveItem> get(String id) {
    return realm.where(ArchiveItemRecord.class)
                .equalTo("id", id).findAll().asObservable()
                .map((rs) -> {
                  if (rs.size() == 0) {
                    return null;
                  } else {
                    return ArchiveItemRecord.mapToDomain(rs.first());
                  }
                })
                // todo give realm its own thread, but this will enforce single threading for now
                .subscribeOn(AndroidSchedulers.mainThread());
  }

  @Override public void put(ArchiveItem feedItem) {
    ArchiveItemRecord record = ArchiveItemRecord.mapToRecord(feedItem);
    realm.beginTransaction();
    realm.insertOrUpdate(record);
    realm.commitTransaction();
  }
}
