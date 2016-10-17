package gregpearce.archivorg.database;

import gregpearce.archivorg.database.model.ArchiveItemRecord;
import gregpearce.archivorg.database.util.RealmUtil;
import gregpearce.archivorg.domain.database.ItemRepository;
import gregpearce.archivorg.domain.model.ArchiveItem;
import gregpearce.archivorg.util.RxUtil;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

import static gregpearce.archivorg.database.util.RealmUtil.getRealm;
import static gregpearce.archivorg.database.util.RealmUtil.doRealmTransaction;

public class ItemRepositoryImpl implements ItemRepository {

  @Inject public ItemRepositoryImpl() {
  }

  private RealmQuery<ArchiveItemRecord> getArchiveItems() {
    return getRealm().where(ArchiveItemRecord.class);
  }

  @Override public Observable<List<ArchiveItem>> getBookmarks() {
    return RxUtil.bootstrap(() -> getBookmarksFromRealm())
                 .map((records) -> ArchiveItemRecord.mapToDomainList(records))
                 .compose(RealmUtil.subscribeDefaults());
  }

  private Observable<RealmResults<ArchiveItemRecord>> getBookmarksFromRealm() {
    return getArchiveItems().isNotNull("bookmarkedDate")
                            .findAllSorted("bookmarkedDate", Sort.DESCENDING)
                            .asObservable();
  }

  @Override public Observable<ArchiveItem> get(String id) {
    return RxUtil.bootstrap(() -> getFromRealm(id).asObservable())
                 .map((result) -> result == null ? null : ArchiveItemRecord.mapToDomain(result))
                 .compose(RealmUtil.subscribeDefaults());
  }

  private Observable<ArchiveItemRecord> getFromRealm(String id) {
    return getArchiveItems().equalTo("id", id)
                            // even though we are querying by id, if we do findFirst then if it
                            // doesn't exist we can't build an observable off it
                            .findAll()
                            .asObservable()
                            .map(result -> {
                              // if it doesn't exist, map to null, otherwise get it
                              if (result.size() == 0) {
                                return null;
                              } else {
                                return result.first();
                              }
                            });
  }

  @Override public void put(ArchiveItem feedItem) {
    doRealmTransaction(realm -> {
      ArchiveItemRecord record = ArchiveItemRecord.mapToRecord(feedItem);
      realm.insertOrUpdate(record);
    });
  }

  @Override public void delete(String id) {
    doRealmTransaction(realm ->
                           getArchiveItems()
                               .equalTo("id", id)
                               .findFirst()
                               .deleteFromRealm());
  }
}
