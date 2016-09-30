package gregpearce.archivorg.domain.database;

import gregpearce.archivorg.domain.model.ArchiveItem;
import java.util.List;
import rx.Observable;

public interface ItemRepository {
  Observable<List<ArchiveItem>> getBookmarkedItems();

  Observable<ArchiveItem> get(String id);

  void put(ArchiveItem feedItem);
}
