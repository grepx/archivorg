package gregpearce.archivorg.data.database.mapper;

import org.threeten.bp.Instant;

public class InstantMapper {
  public static Instant toInstant(String record) {
    if (record != null) {
      return Instant.parse(record);
    } else {
      return null;
    }
  }

  public static String toRecord(Instant instant) {
    return instant == null ? null : instant.toString();
  }
}
