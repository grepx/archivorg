package gregpearce.archivorg.domain.feed;

import com.google.auto.factory.AutoFactory;
import gregpearce.archivorg.domain.BasePresenter;
import gregpearce.archivorg.domain.model.FeedItem;
import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.network.FeedService;
import gregpearce.archivorg.util.RxUtil;
import java.util.ArrayList;
import java.util.Collections;
import rx.Subscription;

@AutoFactory
public class FeedPresenter extends BasePresenter<FeedView, FeedViewState> {

  private FeedService feedService;

  private Subscription nextPageSubscription = null;
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

  @Override public void onStart() {
    fetchPage();
  }

  private void reset() {
    if (nextPageSubscription != null) {
      nextPageSubscription.unsubscribe();
    }
    nextPageSubscription = null;
    updateViewState(initViewState());
    reachedBottomOfFeed = false;
    nextPageToFetch = 1;
  }

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

  private static final int LOAD_NEXT_PAGE_MARGIN = 10;

  private void fetchPage() {
    if (reachedBottomOfFeed || nextPageSubscription != null) {
      return;
    }
    nextPageSubscription =
        feedService.getPage(nextPageToFetch)
                   .compose(RxUtil.subscribeDefaults())
                   .subscribe(
                       result -> {
                         showPage(result);
                         nextPageSubscription = null;
                         nextPageToFetch++;
                       },
                       error -> {
                         showError();
                         nextPageSubscription = null;
                       });
    registerSubscription(nextPageSubscription);
  }

  private void showPage(ResultPage page) {
    reachedBottomOfFeed = page.isLastPage();
    boolean feedIsEmpty = reachedBottomOfFeed &&
                          viewState.feedItems().size() == 0 && page.results().size() == 0;

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