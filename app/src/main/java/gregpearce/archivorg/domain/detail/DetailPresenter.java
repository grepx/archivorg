package gregpearce.archivorg.domain.detail;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import gregpearce.archivorg.domain.BasePresenter;
import gregpearce.archivorg.domain.database.ItemRepository;
import gregpearce.archivorg.domain.model.ArchiveItem;
import gregpearce.archivorg.network.DetailService;
import gregpearce.archivorg.util.RxUtil;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

@AutoFactory
public class DetailPresenter extends BasePresenter<DetailView, DetailViewState> {

  private String id;
  private DetailService detailService;
  private ItemRepository bookmarkRepository;
  private LinkSharer linkSharer;

  private ArchiveItem item;

  public DetailPresenter(String id,
                         @Provided DetailService detailService,
                         @Provided ItemRepository bookmarkRepository,
                         @Provided LinkSharer linkSharer) {
    this.id = id;
    this.detailService = detailService;
    this.bookmarkRepository = bookmarkRepository;
    this.linkSharer = linkSharer;
  }

  @Override protected DetailViewState initViewState() {
    return DetailViewState.builder()
                          .item(null)
                          .screen(DetailViewState.Screen.Loading)
                          .build();
  }

  @Override public void onStart() {
    loadItem();
  }

  private void loadItem() {
    Timber.d("Loading item from repository");
    Subscription subscription =
        bookmarkRepository.get(id)
                          .compose(RxUtil.subscribeDefaults())
                          .subscribe(item -> {
                            processRepositoryResult(item);
                          }, error -> {
                            Timber.e("Error loading %s", id);
                            showError();
                          }, () -> {
                            Timber.e("Error: Completed stream for %s", id);
                          });
    registerSubscription(subscription);
  }

  private void processRepositoryResult(ArchiveItem item) {
    if (item == null) {
      // the item doesn't exist in the database, trigger a network load
      Timber.d("Item was not available in repository, loading from network");
      fetchItem();
    } else {
      Timber.d("Loaded item from repository: %s", item);
      showItem(item);
    }
  }

  private void fetchItem() {
    Observable<ArchiveItem> network = detailService.get(id);

    Subscription subscription =
        network
            .compose(RxUtil.subscribeDefaults())
            .subscribe(item -> {
              Timber.d("Fetched item from network: %s", item);
              showItem(item);
            }, error -> {
              showError();
            });
    registerSubscription(subscription);
  }

  private void showItem(ArchiveItem item) {
    this.item = item;
    updateViewState(viewState.toBuilder()
                             .screen(DetailViewState.Screen.Detail)
                             .item(item)
                             .build());
  }

  private void showError() {
    updateViewState(viewState.toBuilder()
                             .screen(DetailViewState.Screen.Error)
                             .build());
  }

  public void refresh() {
    updateViewState(initViewState());
    fetchItem();
  }

  public void share() {
    linkSharer.share(item.id());
  }

  public void bookmark() {
    // toggle the bookmarked value and save
    ArchiveItem updatedItem = item.toBuilder()
                                  .isBookmarked(!item.isBookmarked())
                                  .build();
    bookmarkRepository.put(updatedItem);
  }
}