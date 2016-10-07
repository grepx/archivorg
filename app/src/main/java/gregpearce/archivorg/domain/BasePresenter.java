package gregpearce.archivorg.domain;

public abstract class BasePresenter<ViewType extends BaseView<ViewStateType>, ViewStateType> {

  protected ViewStateType viewState;
  protected ViewType view;

  public BasePresenter() {
    viewState = initViewState();
  }

  public ViewStateType subscribe(ViewType view) {
    this.view = view;
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

  /**
   * Called once, by the owner to inform the Presenter it should start.
   * (e.g. use this to kick off a network request that happens  when the screen starts)
   */
  public void onStart() {
  }

  /**
   * Called by owner to inform the Presenter that it should clean up
   * (e.g. unsubscribe rxJava subscriptions).
   */
  public void onDestroy() {
  }
}
