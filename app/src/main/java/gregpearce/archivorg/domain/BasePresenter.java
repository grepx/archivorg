package gregpearce.archivorg.domain;

import gregpearce.archivorg.util.NullSafe;

public abstract class BasePresenter<ViewType extends BaseView> {
  private boolean started = false;
  private boolean running = false;

  public void start() {
    started = true;
  }

  public void resume() {
    running = true;
  }

  public void pause() {
    running = false;
  }

  public boolean isStarted() {
    return started;
  }

  public boolean isNotPaused() {
    return running;
  }

  protected NullSafe<ViewType> view = new NullSafe<>();

  public void registerView(ViewType view) {
    this.view = new NullSafe<>(view);
    syncView(view);
  }

  /**
   * Always unregister the view from the presenter onPause. Other objects might hold references to this Presenter,
   * which could in turn memory leak the view.
   */
  public void unregisterView() {
    this.view = new NullSafe<>();
  }

  /**
   * Always implement this to sync up the view state with the presenter state when the view is registered.
   * The view may have been paused while the presenter was performing work.
   * Even on the first subscribe call, the view's initial state may not be the same as the presenter's.
   */
  protected abstract void syncView(ViewType view);
}
