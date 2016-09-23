package gregpearce.archivorg.domain.feed;

import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.network.FeedService;
import gregpearce.archivorg.util.RxUtil;
import java.util.Collections;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import timber.log.Timber;

import static gregpearce.archivorg.domain.feed.FeedViewState.from;

public class FeedPresenter {

  private FeedService feedService;

  private boolean started = false;
  private boolean fetchingNextPage = false;
  private boolean reachedBottomOfFeed = false;
  private int nextPageToFetch = 1;

  private FeedViewState viewState =
      ImmutableFeedViewState.builder()
                            .showBottomLoading(false)
                            .showError(false)
                            .refreshing(true)
                            .feedItems(Collections.EMPTY_LIST)
                            .build();
  BehaviorSubject<FeedViewState> viewState$ = BehaviorSubject.create(viewState);

  public FeedPresenter(FeedService feedService) {
    this.feedService = feedService;
  }

  public Observable<FeedViewState> getViewState() {
    start();
    return viewState$.asObservable().distinctUntilChanged();
    //return viewMutations.asObservable()
    //                    // use the stream of view state mutators to mutate the view state
    //                    .scan(INITIAL_VIEW_STATE,
    //                          (currentState, mutator) -> mutator.call(currentState))
    //                    // the mutators will often just set something that was already set
    //                    // for efficiency, eliminate the view from receiving duplicates
    //                    .distinctUntilChanged();
  }

  private void start() {
    if (!started) {
      started = true;
      fetchPage();
    }
  }

  public void scrolledToIndex(int index) {
    Timber.d("Scrolled to index: %d", index);
    if (!reachedBottomOfFeed && !fetchingNextPage) {
      final int LOAD_NEXT_PAGE_MARGIN = 10;
      if (index > viewState.feedItems().size() - LOAD_NEXT_PAGE_MARGIN) {
        fetchPage();
        nextPageToFetch++;
      }
    }
  }

  public void refresh() {
    viewState = from(viewState)
        .showBottomLoading(false)
        .showError(false)
        .refreshing(true)
        .feedItems(Collections.EMPTY_LIST)
        .build();
    updateViewState();

    nextPageToFetch = 1;
    fetchPage();
  }

  private void fetchPage() {
    fetchingNextPage = true;
    Observable<ResultPage> serviceCall;

    serviceCall = feedService.getPage(nextPageToFetch);

    serviceCall.compose(RxUtil.subscribeDefaults()).subscribe(
        result -> {
          showPage(result);
          fetchingNextPage = false;
        },
        error -> {
          showError();
          fetchingNextPage = false;
        });
  }

  private void showPage(ResultPage page) {
    reachedBottomOfFeed = page.isLastPage();
    // update presenter state
    viewState = from(viewState)
        .addAllFeedItems(page.results())
        .showBottomLoading(!reachedBottomOfFeed)
        .refreshing(false)
        .build();
    updateViewState();
  }

  private void showError() {
    viewState = from(viewState)
        .showError(true)
        .refreshing(false)
        .showBottomLoading(false)
        .build();
    updateViewState();
  }

  private void updateViewState() {
    viewState$.onNext(viewState);
  }
}
