package gregpearce.archivorg.data.database.model;

import gregpearce.archivorg.domain.model.ArchiveFile;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import java.util.ArrayList;
import java.util.List;

public class ArchiveFileRecord extends RealmObject {

  //public @Required @PrimaryKey String archiveItemId;

  //public @Required @PrimaryKey int index;

  public @Required @PrimaryKey String md5;

  public String name;

  public String size;

  public String length;

  public String source;

  public String format;

  public static ArchiveFileRecord mapToRecord(ArchiveFile archiveItem) {
    ArchiveFileRecord record = new ArchiveFileRecord();
    record.md5 = archiveItem.md5();
    record.name = archiveItem.name();
    record.size = archiveItem.size();
    record.length = archiveItem.length();
    record.source = archiveItem.source();
    record.format = archiveItem.format();
    return record;
  }

  public static RealmList<ArchiveFileRecord> mapToRecordList(List<ArchiveFile> archiveFiles) {
    RealmList<ArchiveFileRecord> records = new RealmList<>();
    for (int i = 0; i < archiveFiles.size(); i++) {
      ArchiveFile archiveFile = archiveFiles.get(i);
      records.add(mapToRecord(archiveFile));
    }
    return records;
  }

  public static ArchiveFile mapToDomain(ArchiveFileRecord record) {
    ArchiveFile archiveItem =
        ArchiveFile.builder()
                   .name(record.name)
                   .size(record.size)
                   .length(record.length)
                   .source(record.source)
                   .format(record.format)
                   .md5(record.md5)
                   .build();
    return archiveItem;
  }

  public static List<ArchiveFile> mapToDomainList(RealmList<ArchiveFileRecord> records) {
    ArrayList<ArchiveFile> archiveItems = new ArrayList<>(records.size());
    for (ArchiveFileRecord record : records) {
      archiveItems.add(mapToDomain(record));
    }
    return archiveItems;
  }
}
