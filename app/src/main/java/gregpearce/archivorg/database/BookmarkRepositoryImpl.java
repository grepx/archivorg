package gregpearce.archivorg.database;

import gregpearce.archivorg.domain.database.ItemRepository;
import gregpearce.archivorg.domain.model.ArchiveItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.subjects.BehaviorSubject;

public class BookmarkRepositoryImpl implements ItemRepository {
  HashMap<String, ArchiveItem> repository = new HashMap<>();

  BehaviorSubject<List<ArchiveItem>> subject = BehaviorSubject.create(Collections.EMPTY_LIST);

  @Inject public BookmarkRepositoryImpl() {
  }

  @Override public Observable<List<ArchiveItem>> getBookmarkedItems() {
    return subject.asObservable();
  }

  @Override public Observable<ArchiveItem> get(String id) {
    return getBookmarkedItems()
        .map(archiveItems -> {
          for (ArchiveItem archiveItem : archiveItems) {
            if (archiveItem.id().equals(id)) {
              return archiveItem;
            }
          }
          return null;
        });
  }

  @Override public void put(ArchiveItem feedItem) {
    repository.put(feedItem.id(), feedItem);
    subject.onNext(new ArrayList<>(repository.values()));
  }
}
