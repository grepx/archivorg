package gregpearce.archivorg.ui.detail;

import javax.inject.Inject;

import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.model.ArchiveItem;
import gregpearce.archivorg.network.DetailService;
import gregpearce.archivorg.ui.BasePresenter;
import gregpearce.archivorg.util.RxUtil;
import timber.log.Timber;

@ActivityScope
public class DetailPresenter extends BasePresenter<DetailView> {

  @Inject DetailService detailService;

  private ArchiveItem archiveItem;

  @Inject public DetailPresenter() {
  }

  public void start(String id) {
    detailService.get(id)
        .compose(RxUtil.subscribeDefaults())
        .subscribe(
            item -> {
              Timber.d("Loaded archive item, id: %s", id);
              view.notNull(view -> view.updateItem(item));
            },
            error -> {
              view.notNull(view -> view.showError());
            });
  }
}