package gregpearce.archivorg.domain;

import com.bluelinelabs.conductor.Router;

public interface Navigator {
  void navigateToDiscover();

  void navigateToDetail(String itemId);
}
