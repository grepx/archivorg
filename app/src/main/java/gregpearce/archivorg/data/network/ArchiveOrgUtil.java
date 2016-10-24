package gregpearce.archivorg.data.network;

import android.support.annotation.Nullable;
import gregpearce.archivorg.domain.model.MediaType;
import org.threeten.bp.Instant;

public class ArchiveOrgUtil {
  public static Instant parseDateApiV1(@Nullable String date) {
    if (date == null) {
      return null;
    }
    return Instant.parse(date);
  }

  public static Instant parseDateApiV2(@Nullable String date) {
    if (date == null) {
      return null;
    }
    // the V2 date format is: yyyy-mm-dd hh:mm:ss
    // this doesn't match a java.time standard template
    // the easiest way to get it into instant format is to just add the T and Z
    date = date.replace(" ", "T");
    date = date + "Z";
    return Instant.parse(date);
  }

  public static MediaType parseMediaType(@Nullable String mediatype, @Nullable String type) {
    if ("movies".equals(mediatype)) {
      return MediaType.Video;
    } else if ("audio".equals(mediatype) || "etree".equals(mediatype) || "sound".equals(type)) {
      return MediaType.Audio;
    } else if ("texts".equals(mediatype)) {
      return MediaType.Book;
    } else if ("image".equals(mediatype)) {
      return MediaType.Image;
    } else {
      return MediaType.Unknown;
    }
  }
}
