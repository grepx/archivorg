package gregpearce.archivorg.util;

import android.view.View;

public class ViewUtil {
  public static void setVisible(View view, boolean visible) {
    if (visible) {
      view.setVisibility(View.VISIBLE);
    } else {
      view.setVisibility(View.GONE);
    }
  }
}
