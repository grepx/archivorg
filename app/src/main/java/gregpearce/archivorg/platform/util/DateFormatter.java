package gregpearce.archivorg.platform.util;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

public class DateFormatter {
  private static final DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("dd MMM yyyy").withZone(ZoneId.systemDefault());

  public static String formatDate(Instant date) {
    return formatter.format(date);
  }
}
