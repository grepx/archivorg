package gregpearce.archivorg.domain.feed;

import com.google.auto.factory.AutoFactory;
import gregpearce.archivorg.domain.BasePresenter;
import gregpearce.archivorg.domain.model.FeedItem;
import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.network.FeedService;
import gregpearce.archivorg.util.RxDevUtil;
import gregpearce.archivorg.util.RxUtil;
import java.util.ArrayList;
import java.util.Collections;

@AutoFactory
public class FeedPresenter extends BasePresenter<FeedView, FeedViewState> {

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
                        .showRefreshing(true)
                        .showEmptyFeedMessage(false)
                        .feedItems(Collections.EMPTY_LIST)
                        .build();
  }

  @Override protected void start() {
    fetchPage();
  }

  private static final int LOAD_NEXT_PAGE_MARGIN = 10;

  public void scrolledToIndex(int index) {
    if (index > viewState.feedItems().size() - LOAD_NEXT_PAGE_MARGIN) {
      fetchPage();
    }
  }

  public void refresh() {
    reset();
    fetchPage();
  }

  public void retry() {
    updateViewState(viewState.toBuilder()
                             .showError(false)
                             .showBottomLoading(nextPageToFetch != 1)
                             .showRefreshing(nextPageToFetch == 1)
                             .build());
    fetchPage();
  }

  private void reset() {
    updateViewState(initViewState());
    fetchingNextPage = false;
    reachedBottomOfFeed = false;
    nextPageToFetch = 1;
  }

  private void fetchPage() {
    if (reachedBottomOfFeed || fetchingNextPage) {
      return;
    }
    fetchingNextPage = true;
    feedService.getPage(nextPageToFetch)
               .compose(RxUtil.subscribeDefaults())
               .subscribe(
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
                             .showRefreshing(false)
                             .build());
  }

  private void showError() {
    updateViewState(viewState.toBuilder()
                             .showError(true)
                             .showRefreshing(false)
                             .showBottomLoading(false)
                             .build());
  }
}