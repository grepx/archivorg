package gregpearce.archivorg.network;

import android.support.annotation.Nullable;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import gregpearce.archivorg.model.MediaType;

public class ArchiveOrgUtil {
  public static LocalDateTime parseDateApiV1(@Nullable String date) {
    if (date == null) {
      return null;
    }
    // remove the Z that archive.org puts on the end of date strings
    date = date.substring(0, date.length() - 1);
    return LocalDateTime.parse(date);
  }

  public static LocalDateTime parseDateApiV2(@Nullable String date) {
    if (date == null) {
      return null;
    }
    return LocalDateTime.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
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
