package gregpearce.archivorg.database;

import gregpearce.archivorg.database.model.ArchiveItemRecord;
import gregpearce.archivorg.domain.database.ItemRepository;
import gregpearce.archivorg.domain.model.ArchiveItem;
import io.realm.Realm;
import io.realm.RealmQuery;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class ItemRepositoryImpl implements ItemRepository {

  private Realm realm;

  @Inject public ItemRepositoryImpl(Realm realm) {
    this.realm = realm;
  }

  private RealmQuery<ArchiveItemRecord> getRecords() {
    return realm.where(ArchiveItemRecord.class);
  }

  @Override public Observable<List<ArchiveItem>> getBookmarkedItems() {
    return getRecords().equalTo("isBookmarked", true).findAll().asObservable()
                       .map((records) -> ArchiveItemRecord.mapToDomainList(records));
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
