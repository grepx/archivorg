package gregpearce.archivorg.ui.util;

import android.content.Context;
import android.view.View;
import gregpearce.archivorg.di.ActivityComponent;
import gregpearce.archivorg.ui.BaseActivity;

public class ComponentUtil {
  /**
   * Can be used to get a component from any View to perform Activity scope DI.
   */
  public static ActivityComponent getComponent(View view) {
    Context context = view.getContext();
    if (context instanceof BaseActivity) {
      return ((BaseActivity) view.getContext()).getComponent();
    } else {
      throw new RuntimeException("View Context was not a BaseActivity instance.");
    }
  }
}
