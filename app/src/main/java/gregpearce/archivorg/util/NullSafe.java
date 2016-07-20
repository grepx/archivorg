package gregpearce.archivorg.util;

public final class NullSafe<Type> {

  private Type value;

  public NullSafe() {
  }

  public NullSafe(Type value) {
    this.value = value;
  }

  public interface Lambda<Type> {
    void f(Type value);
  }

  public void notNull(Lambda<Type> lambda) {
    if (value != null) {
      lambda.f(value);
    }
  }
}
