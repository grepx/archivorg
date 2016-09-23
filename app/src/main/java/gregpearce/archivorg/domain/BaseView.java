package gregpearce.archivorg.domain;

public interface BaseView<ViewState> {
  void update(ViewState updatedViewState);
}
