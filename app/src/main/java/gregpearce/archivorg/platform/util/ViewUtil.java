package gregpearce.archivorg.platform.util;

import android.view.View;

public class ViewUtil {
  public static void setVisible(boolean visible, View view) {
    if (visible) {
      view.setVisibility(View.VISIBLE);
    } else {
      view.setVisibility(View.GONE);
    }
  }

  public static void setVisible(boolean visible, View... views) {
    for (View view : views) {
      if (visible) {
        view.setVisibility(View.VISIBLE);
      } else {
        view.setVisibility(View.GONE);
      }
    }
  }
}
