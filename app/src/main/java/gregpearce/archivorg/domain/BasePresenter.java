package gregpearce.archivorg.domain;

public abstract class BasePresenter<ViewType extends BaseView<ViewStateType>, ViewStateType> {
  private boolean started = false;

  protected ViewStateType viewState;
  protected ViewType view;

  public ViewStateType subscribe(ViewType view) {
    this.view = view;

    if (!started) {
      started = true;
      viewState = initViewState();
      start();
    }

    return viewState;
  }

  public void unsubscribe() {
    view = null;
  }

  protected void updateViewState(ViewStateType newViewState) {
    viewState = newViewState;
    if (view != null) {
      view.update(viewState);
    }
  }

  protected void start() {
  }

  protected abstract ViewStateType initViewState();
}
