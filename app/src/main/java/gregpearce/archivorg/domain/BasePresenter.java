package gregpearce.archivorg.domain;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<ViewType extends BaseView<ViewStateType>, ViewStateType> {

  protected ViewStateType viewState;
  protected ViewType view;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();

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
   * Can be used to register a subscription so that it will automatically be unsubscribed
   * during onDestroy().
   * You may wish to also manage your subscriptions in other more fine-grained ways, but you should
   * always register here to take care of the onDestroy() unsubscribe.
   */
  protected void registerSubscription(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  /**
   * You may wish to drop all subscriptions and reset at some point before onDestroy,
   * for instance if you start a new network request that invalidates the old one.
   */
  protected void clearSubscriptions() {
    compositeSubscription.unsubscribe();
    compositeSubscription = new CompositeSubscription();
  }

  /**
   * Called once, by the owner to inform the Presenter it should start.
   * For instance, you could use this to kick off a network request that happens at the start.
   */
  public void onStart() {
  }

  /**
   * Called by owner to inform the Presenter that it should clean up.
   */
  public void onDestroy() {
    compositeSubscription.unsubscribe();
  }
}
