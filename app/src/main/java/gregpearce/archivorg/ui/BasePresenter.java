package gregpearce.archivorg.ui;

import gregpearce.archivorg.util.NullSafe;

public class BasePresenter<ViewType> {
  protected NullSafe<ViewType> view = new NullSafe<>();

  public void registerView(ViewType view) {
    this.view = new NullSafe<>(view);
  }
}
