package gregpearce.archivorg.util;

public class NullUtil {
  public static String defaultNullValue(String s) {
    return (s == null) ? "" : s;
  }
}
