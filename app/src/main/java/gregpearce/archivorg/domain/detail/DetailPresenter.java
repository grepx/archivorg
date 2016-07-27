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
    start();
  }

  @Override public void start() {
    super.start();
    detailService.get(id)
        .compose(RxUtil.subscribeDefaults())
        .subscribe(
            item -> {
              Timber.d("Loaded archive item, id: %s", id);
              archiveItem = item;
              view.notNull(view -> {
                view.updateLoading(false);
                view.updateItem(archiveItem);
              });
            },
            error -> {
              view.notNull(view -> {
                view.updateLoading(false);
                view.showError();
              });
            });
  }

  @Override protected void syncView(DetailView view) {
    if (archiveItem != null) {
      view.updateLoading(false);
      view.updateItem(archiveItem);
    } else {
      view.updateLoading(true);
    }
  }
}