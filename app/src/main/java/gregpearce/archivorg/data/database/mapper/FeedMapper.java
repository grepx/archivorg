package gregpearce.archivorg.data.database.mapper;

import gregpearce.archivorg.data.database.model.ArchiveItemRecord;
import gregpearce.archivorg.domain.Constants;
import gregpearce.archivorg.domain.model.FeedItem;
import gregpearce.archivorg.domain.model.ResultPage;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

public class FeedMapper {
  public static ResultPage toResultPage(RealmResults<ArchiveItemRecord> records, int page) {
    // convert page to 0 indexed
    page = page - 1;
    int pageSize = Constants.PAGE_SIZE;

    List<FeedItem> feedItems = new ArrayList<>(pageSize);
    int index = pageSize * page;
    while (index < index + pageSize && index < records.size()) {
      FeedItem feedItem = toFeedItem(records.get(index));
      feedItems.add(feedItem);
      index++;
    }

    boolean isLastPage = index == records.size();

    return ResultPage.create(feedItems, records.size(), page, isLastPage);
  }

  public static FeedItem toFeedItem(ArchiveItemRecord archiveItemRecord) {
    return FeedItem.builder()
                   .id(archiveItemRecord.id)
                   .title(archiveItemRecord.title)
                   .description(archiveItemRecord.description)
                   .mediaType(MediaTypeMapper.toMediaType(archiveItemRecord.mediaType))
                   .publishedDate(InstantMapper.toInstant(archiveItemRecord.publishedDate))
                   .build();
  }
}
