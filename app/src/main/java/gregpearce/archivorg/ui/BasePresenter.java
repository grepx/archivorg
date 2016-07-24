package gregpearce.archivorg.ui;

import gregpearce.archivorg.util.NullSafe;

public class BasePresenter<ViewType> {
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

  public boolean isRunning() {
    return running;
  }

  protected NullSafe<ViewType> view = new NullSafe<>();

  public void registerView(ViewType view) {
    this.view = new NullSafe<>(view);
  }
}
