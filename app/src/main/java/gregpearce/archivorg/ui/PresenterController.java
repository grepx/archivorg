package gregpearce.archivorg.ui;

import android.os.Bundle;
import gregpearce.archivorg.domain.BasePresenter;

public abstract class PresenterController extends BaseController {
  BasePresenter presenter;

  public PresenterController() {
  }

  public PresenterController(Bundle args) {
    super(args);
  }

  protected void registerPresenter(BasePresenter presenter) {
    this.presenter = presenter;
    presenter.onStart();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (presenter != null) {
      presenter.onDestroy();
    }
  }
}
