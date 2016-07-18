package gregpearce.archivorg.ui;

public class BasePresenter<ViewType> {
  protected ViewType view;

  public void registerView(ViewType view) {
    this.view = view;
  }

}
