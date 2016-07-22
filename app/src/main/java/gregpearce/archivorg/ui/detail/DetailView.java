package gregpearce.archivorg.ui.detail;

import gregpearce.archivorg.model.ArchiveItem;

public interface DetailView {
  void updateLoading(boolean isLoading);

  void updateItem(ArchiveItem archiveItem);

  void showError();
}
