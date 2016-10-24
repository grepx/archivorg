package gregpearce.archivorg.platform;

import com.bluelinelabs.conductor.RouterTransaction;

public interface OverlayChildRouter {
  void pushOverlayController(RouterTransaction transaction);
}
