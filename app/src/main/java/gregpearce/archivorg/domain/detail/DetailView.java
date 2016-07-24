package gregpearce.archivorg.domain.detail;

import gregpearce.archivorg.domain.model.ArchiveItem;

public interface DetailView {
  void updateLoading(boolean isLoading);

  void updateItem(ArchiveItem archiveItem);

  void showError();
}
