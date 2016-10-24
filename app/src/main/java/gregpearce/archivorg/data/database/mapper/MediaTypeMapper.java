package gregpearce.archivorg.data.database.mapper;

import gregpearce.archivorg.domain.model.MediaType;

public class MediaTypeMapper {
  public static MediaType toMediaType(String record) {
    return MediaType.valueOf(record);
  }

  public static String toRecord(MediaType mediaType) {
    return mediaType.name();
  }
}
