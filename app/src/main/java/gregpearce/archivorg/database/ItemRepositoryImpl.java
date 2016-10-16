package gregpearce.archivorg.database;

import gregpearce.archivorg.database.model.ArchiveItemRecord;
import gregpearce.archivorg.database.util.RealmUtil;
import gregpearce.archivorg.domain.database.ItemRepository;
import gregpearce.archivorg.domain.model.ArchiveItem;
import gregpearce.archivorg.util.RxUtil;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

import static gregpearce.archivorg.database.util.RealmUtil.getRealm;
import static gregpearce.archivorg.database.util.RealmUtil.postToHandler;

public class ItemRepositoryImpl implements ItemRepository {

  @Inject public ItemRepositoryImpl() {
  }

  private RealmQuery<ArchiveItemRecord> getArchiveItems() {
    return getRealm().where(ArchiveItemRecord.class);
  }

  @Override public Observable<List<ArchiveItem>> getBookmarkedItems() {
    return getArchiveItems().equalTo("isBookmarked", true).findAll().asObservable()
                            .map((records) -> ArchiveItemRecord.mapToDomainList(records))
                            .compose(RealmUtil.subscribeDefaults());
  }

  @Override public Observable<ArchiveItem> get(String id) {
    return RxUtil.bootstrap()
                 .flatMap(na -> getFromRealm(id).asObservable())
                 .map((result) -> {
                   if (result.size() == 0) {
                     return null;
                   } else {
                     return ArchiveItemRecord.mapToDomain(result.first());
                   }
                 })
                 .compose(RealmUtil.subscribeDefaults());
  }

  private RealmResults<ArchiveItemRecord> getFromRealm(String id) {
    return getArchiveItems().equalTo("id", id).findAll();
  }

  @Override public void put(ArchiveItem feedItem) {
    postToHandler(realm -> {
      ArchiveItemRecord record = ArchiveItemRecord.mapToRecord(feedItem);
      realm.beginTransaction();
      realm.insertOrUpdate(record);
      realm.commitTransaction();
    });
  }
}
