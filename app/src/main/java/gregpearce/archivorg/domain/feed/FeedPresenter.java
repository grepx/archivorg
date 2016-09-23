package gregpearce.archivorg.domain.feed;

import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.network.FeedService;
import gregpearce.archivorg.util.RxUtil;
import java.util.Collections;
import rx.Observable;
import timber.log.Timber;

public class FeedPresenter {

  private FeedService feedService;

  private boolean started = false;
  private boolean fetchingNextPage = false;
  private boolean reachedBottomOfFeed = false;
  private int nextPageToFetch = 1;

  private FeedView view;

  private FeedViewState viewState =
      ImmutableFeedViewState.builder()
                            .showBottomLoading(false)
                            .showError(false)
                            .refreshing(true)
                            .showEmptyFeedMessage(false)
                            .feedItems(Collections.EMPTY_LIST)
                            .build();

  public FeedPresenter(FeedService feedService) {
    this.feedService = feedService;
  }

  public FeedViewState subscribe(FeedView view) {
    this.view = view;

    start();

    return viewState;
  }

  public void unsubscribe() {
    view = null;
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
      }
    }
  }

  public void refresh() {
    viewState = FeedViewState.from(viewState)
        .showBottomLoading(false)
        .showError(false)
        .refreshing(true)
        .showEmptyFeedMessage(false)
        .feedItems(Collections.EMPTY_LIST)
        .build();
    updateView();

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
          nextPageToFetch++;
        },
        error -> {
          showError();
          fetchingNextPage = false;
        });
  }

  private void showPage(ResultPage page) {
    reachedBottomOfFeed = page.isLastPage();
    boolean feedIsEmpty = reachedBottomOfFeed &&
                          viewState.feedItems().size() == 0 && page.results().size() == 0;
    // update presenter state
    viewState = FeedViewState.from(viewState)
        .addAllFeedItems(page.results())
        .showBottomLoading(!reachedBottomOfFeed)
        .showEmptyFeedMessage(feedIsEmpty)
        .refreshing(false)
        .build();
    updateView();
  }

  private void showError() {
    viewState = FeedViewState.from(viewState)
        .showError(true)
        .refreshing(false)
        .showBottomLoading(false)
        .build();
    updateView();
  }

  private void updateView() {
    if (view != null) {
      view.update(viewState);
    }
  }
}
