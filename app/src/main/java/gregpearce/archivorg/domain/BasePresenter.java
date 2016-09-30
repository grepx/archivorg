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

  protected abstract ViewStateType initViewState();

  protected void start() {
  }

  /**
   * This can be overriden by the Presenter and called by it's Controller / parent class to inform
   * it that it should
   */
  public void onDestroy() {
  }
}
