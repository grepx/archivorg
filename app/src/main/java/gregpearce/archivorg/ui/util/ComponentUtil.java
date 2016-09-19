package gregpearce.archivorg.ui.util;

import android.content.Context;
import android.view.View;
import gregpearce.archivorg.di.ActivityComponent;
import gregpearce.archivorg.ui.activity.ActivityComponentProvider;

public class ComponentUtil {
  /**
   * Can be used to get a component from any View to perform Activity scope DI.
   */
  public static ActivityComponent getComponent(View view) {
    Context context = view.getContext();
    if (context instanceof ActivityComponentProvider) {
      return ((ActivityComponentProvider) view.getContext()).getActivityComponent();
    } else {
      throw new RuntimeException("View Context was not a ActivityComponentProvider instance.");
    }
  }
}
