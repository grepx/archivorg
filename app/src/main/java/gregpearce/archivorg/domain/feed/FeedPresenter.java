package gregpearce.archivorg.domain.feed;

import com.google.auto.factory.AutoFactory;
import gregpearce.archivorg.domain.BasePresenter2;
import gregpearce.archivorg.domain.model.FeedItem;
import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.network.FeedService;
import gregpearce.archivorg.util.RxUtil;
import java.util.ArrayList;
import java.util.Collections;
import rx.Observable;
import timber.log.Timber;

@AutoFactory
public class FeedPresenter extends BasePresenter2<FeedView, FeedViewState> {

  private FeedService feedService;

  private boolean fetchingNextPage = false;
  private boolean reachedBottomOfFeed = false;
  private int nextPageToFetch = 1;

  public FeedPresenter(FeedService feedService) {
    this.feedService = feedService;
  }

  @Override protected FeedViewState initViewState() {
    return FeedViewState.builder()
                        .showBottomLoading(false)
                        .showError(false)
                        .refreshing(true)
                        .showEmptyFeedMessage(false)
                        .feedItems(Collections.EMPTY_LIST)
                        .build();
  }

  @Override protected void start() {
    fetchPage();
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
    updateViewState(viewState.toBuilder()
                             .showBottomLoading(false)
                             .showError(false)
                             .refreshing(true)
                             .showEmptyFeedMessage(false)
                             .feedItems(Collections.EMPTY_LIST)
                             .build());

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
    ArrayList<FeedItem> feedItems =
        new ArrayList<>(viewState.feedItems().size() + page.results().size());
    feedItems.addAll(viewState.feedItems());
    feedItems.addAll(page.results());

    updateViewState(viewState.toBuilder()
                             .feedItems(feedItems)
                             .showBottomLoading(!reachedBottomOfFeed)
                             .showEmptyFeedMessage(feedIsEmpty)
                             .refreshing(false)
                             .build());
  }

  private void showError() {
    updateViewState(viewState.toBuilder()
                             .showError(true)
                             .refreshing(false)
                             .showBottomLoading(false)
                             .build());
  }
}