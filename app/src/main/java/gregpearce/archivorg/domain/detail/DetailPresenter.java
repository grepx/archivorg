package gregpearce.archivorg.domain.detail;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import gregpearce.archivorg.domain.BasePresenter;
import gregpearce.archivorg.network.DetailService;
import gregpearce.archivorg.util.RxDevUtil;
import gregpearce.archivorg.util.RxUtil;
import javax.inject.Inject;
import timber.log.Timber;

@AutoFactory
public class DetailPresenter extends BasePresenter<DetailView, DetailViewState> {

  LinkSharer linkSharer;
  private DetailService detailService;
  private String id;

  public DetailPresenter(String id,
                         @Provided DetailService detailService, @Provided LinkSharer linkSharer) {
    this.id = id;
    this.detailService = detailService;
    this.linkSharer = linkSharer;
  }

  @Override protected DetailViewState initViewState() {
    return DetailViewState.builder()
                          .item(null)
                          .screen(DetailViewState.Screen.Loading)
                          .build();
  }

  @Override protected void start() {
    fetchItem();
  }

  private void fetchItem() {
    detailService.get(id)
                 .compose(RxUtil.subscribeDefaults())
                 .subscribe(item -> {
                   Timber.d("Loaded archive item, id: %s", id);
                   updateViewState(viewState.toBuilder()
                                            .screen(DetailViewState.Screen.Detail)
                                            .item(item)
                                            .build());
                 }, error -> {
                   updateViewState(viewState.toBuilder()
                                            .screen(DetailViewState.Screen.Error)
                                            .build());
                 });
  }

  public void refresh() {
    updateViewState(initViewState());
    fetchItem();
  }

  public void share() {
    linkSharer.share(viewState.item().id());
  }
}