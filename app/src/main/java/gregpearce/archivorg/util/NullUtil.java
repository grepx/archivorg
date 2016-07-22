package gregpearce.archivorg.util;

public class NullUtil {
  public static String defaultValue(String s) {
    return (s == null) ? "" : s;
  }
}
