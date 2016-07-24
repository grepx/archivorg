package gregpearce.archivorg.domain.detail;

import gregpearce.archivorg.domain.BaseView;
import gregpearce.archivorg.domain.model.ArchiveItem;

public interface DetailView extends BaseView {
  void updateLoading(boolean isLoading);

  void updateItem(ArchiveItem archiveItem);

  void showError();
}
