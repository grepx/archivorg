package gregpearce.archivorg.network;

import android.support.annotation.Nullable;

import org.threeten.bp.Instant;

import gregpearce.archivorg.model.MediaType;

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
