package gregpearce.archivorg.database.model;

import gregpearce.archivorg.domain.model.ArchiveItem;
import gregpearce.archivorg.domain.model.MediaType;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.Instant;

public class ArchiveItemRecord extends RealmObject {
  public @Required @PrimaryKey String id;

  public @Required String title;

  public @Required String description;

  //public Instant publishedDate;

  //public @Required  MediaType mediaType;

  public @Required String creator;

  public @Required String uploader;

  //public @Required RealmList<ArchiveFileRecord> files;

  public boolean isBookmarked;

  public static ArchiveItemRecord mapToRecord(ArchiveItem archiveItem) {
    ArchiveItemRecord record = new ArchiveItemRecord();
    record.id = archiveItem.id();
    record.title = archiveItem.title();
    record.description = archiveItem.description();
    record.creator = archiveItem.creator();
    record.uploader = archiveItem.uploader();
    record.isBookmarked = archiveItem.isBookmarked();
    return record;
  }

  public static ArchiveItem mapToDomain(ArchiveItemRecord record) {
    ArchiveItem archiveItem =
        ArchiveItem.builder()
                   .id(record.id)
                   .title(record.title)
                   .description(record.description)
                   .publishedDate(Instant.now()) // todo fix
                   .mediaType(MediaType.Audio) // todo
                   .creator(record.creator)
                   .uploader(record.uploader)
                   .files(new ArrayList<>()) // todo
                   .isBookmarked(record.isBookmarked)
                   .build();
    return archiveItem;
  }

  public static List<ArchiveItem> mapToDomainList(RealmResults<ArchiveItemRecord> records) {
    ArrayList<ArchiveItem> archiveItems = new ArrayList<>(records.size());
    for (ArchiveItemRecord record : records) {
      archiveItems.add(mapToDomain(record));
    }
    return archiveItems;
  }
}
