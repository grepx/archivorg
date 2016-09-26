package gregpearce.archivorg.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.RouterTransaction;

public interface OverlayChildRouter {
  void pushOverlayController(RouterTransaction transaction);
}
