package gregpearce.archivorg.ui.model;

public class StateTransition<T> {
  final private T old;
  final private T current;

  public StateTransition(T current) {
    old = null;
    this.current = current;
  }

  private StateTransition(T old, T current) {
    this.old = old;
    this.current = current;
  }

  public T old() {
    return old;
  }

  public T current() {
    return current;
  }

  public StateTransition<T> next(StateTransition<T> newState) {
    return new StateTransition<>(this.current(), newState.current());
  }
}
