package gregpearce.archivorg.domain.detail;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import gregpearce.archivorg.domain.model.ArchiveItem;

@AutoValue
public abstract class DetailViewState {

  public enum Screen {Loading, Detail, Error}

  // Properties

  public abstract @Nullable ArchiveItem item();

  public abstract Screen screen();

  // Builder

  @AutoValue.Builder
  abstract static class Builder {
    public abstract DetailViewState.Builder item(@Nullable ArchiveItem item);

    public abstract DetailViewState.Builder screen(Screen screen);

    abstract DetailViewState build();
  }

  public abstract DetailViewState.Builder toBuilder();

  static DetailViewState.Builder builder() {
    return new AutoValue_DetailViewState.Builder();
  }
}
