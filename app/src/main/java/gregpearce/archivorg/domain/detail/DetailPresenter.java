package gregpearce.archivorg.domain.detail;

import javax.inject.Inject;

import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.domain.model.ArchiveItem;
import gregpearce.archivorg.network.DetailService;
import gregpearce.archivorg.domain.BasePresenter;
import gregpearce.archivorg.util.RxUtil;
import timber.log.Timber;

@ActivityScope
public class DetailPresenter extends BasePresenter<DetailView> {

  @Inject DetailService detailService;

  private ArchiveItem archiveItem;
  private String id;

  @Inject public DetailPresenter() {
  }

  public void init(String id) {
    this.id = id;
  }

  @Override public void start() {
    super.start();
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