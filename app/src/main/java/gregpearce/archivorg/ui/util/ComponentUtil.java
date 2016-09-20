package gregpearce.archivorg.ui.util;

import android.content.Context;
import android.view.View;
import gregpearce.archivorg.di.ControllerComponent;
import gregpearce.archivorg.ui.activity.MainActivity;

public class ComponentUtil {
  /**
   * Can be used to get a component from any View to perform Controller scope DI.
   */
  public static ControllerComponent getComponent(View view) {
    Context context = view.getContext();
    if (context instanceof MainActivity) {
      return ((MainActivity) view.getContext()).getControllerComponent();
    } else {
      throw new RuntimeException("View Context was not a MainActivity instance.");
    }
  }
}
