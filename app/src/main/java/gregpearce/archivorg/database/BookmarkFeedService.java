package gregpearce.archivorg.database;

import gregpearce.archivorg.database.mapper.FeedMapper;
import gregpearce.archivorg.database.model.ArchiveItemRecord;
import gregpearce.archivorg.database.util.RealmUtil;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.service.FeedService;
import gregpearce.archivorg.util.RxUtil;
import io.realm.RealmResults;
import io.realm.Sort;
import javax.inject.Inject;
import rx.Observable;

import static gregpearce.archivorg.database.util.RealmUtil.doRealmTransaction;
import static gregpearce.archivorg.database.util.RealmUtil.getRealm;

public class BookmarkFeedService implements FeedService {
  @Inject public BookmarkFeedService() {
  }

  @Override public Observable<ResultPage> getPage(int page) {
    return RxUtil.bootstrap(() -> getBookmarksFromRealm())
                 .map((records) -> FeedMapper.toResultPage(records, page))
                 .compose(RealmUtil.subscribeDefaults());
  }

  private Observable<RealmResults<ArchiveItemRecord>> getBookmarksFromRealm() {
    return getRealm().where(ArchiveItemRecord.class)
                     .isNotNull("bookmarkedDate")
                     .findAllSorted("bookmarkedDate", Sort.DESCENDING)
                     .asObservable();
  }
}
