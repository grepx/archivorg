package gregpearce.archivorg.domain.feed;

import java.util.ArrayList;
import java.util.List;

import gregpearce.archivorg.domain.model.FeedItem;
import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.network.FeedService;
import gregpearce.archivorg.domain.BasePresenter;
import gregpearce.archivorg.util.RxUtil;
import rx.Observable;
import timber.log.Timber;

public class FeedPresenter extends BasePresenter<FeedView> {

  FeedService feedService;

  private String query = null;
  private boolean refreshing = false;
  private final List<FeedItem> feedItems = new ArrayList<>();
  private int currentPage = 0;
  private boolean fetchingNextPage = false;
  private boolean reachedBottomOfFeed = false;
  private boolean resultsNeedUpdating = false;

  public FeedPresenter(FeedService feedService) {
    this.feedService = feedService;
  }

  @Override public void start() {
    super.start();
    // do the initial feed load
    search("");
  }

  @Override public void resume() {
    super.resume();
    if (resultsNeedUpdating) {
      updateResults();
    }
  }

  @Override protected void syncView() {
    if (!resultsNeedUpdating) {
      // prevent the view from updating with stale information if we are in the middle of an update
      view.notNull(view -> view.updateFeed(feedItems, reachedBottomOfFeed));
    }
    view.notNull(view -> view.updateRefreshing(this.refreshing));
  }

  public void search(String query) {
    if (query.equals(this.query)) {
      // prevent unnecessary data loss and network calls
      return;
    }
    this.query = query;
    // optimise network calls to only happen when/if a presenter is not paused
    if (isNotPaused()) {
      updateResults();
    } else {
      resultsNeedUpdating = true;
    }
  }

  public void scrolledToIndex(int index) {
    Timber.d("Scrolled to index: %d", index);
    if (!reachedBottomOfFeed && !fetchingNextPage) {
      final int LOAD_NEXT_PAGE_MARGIN = 10;
      if (index > feedItems.size() - LOAD_NEXT_PAGE_MARGIN) {
        currentPage++;
        fetchPage();
      }
    }
  }

  public void refresh() {
    updateResults();
  }

  private void updateResults() {
    resultsNeedUpdating = false;
    if (!refreshing) {
      currentPage = 1;
      reachedBottomOfFeed = false;
      setRefreshing(true);
      fetchPage();
    }
  }

  private void setRefreshing(boolean refreshing) {
    this.refreshing = refreshing;
    view.notNull(view -> view.updateRefreshing(this.refreshing));
  }

  private void fetchPage() {
    fetchingNextPage = true;
    Observable<ResultPage> serviceCall;

    // if the query is empty, get the latest items, otherwise, do a search
    if (query.isEmpty()) {
      serviceCall = feedService.latest(currentPage);
    } else {
      serviceCall = feedService.search(query, currentPage);
    }

    serviceCall.compose(RxUtil.subscribeDefaults())
        .subscribe(
            result -> {
              Timber.d("Feed refresh complete, results count: %d", result.totalCount());
              if (currentPage == 1) {
                feedItems.clear();
              }
              processPage(result);
              setRefreshing(false);
              fetchingNextPage = false;
            },
            error -> {
              view.notNull(view -> view.showError());
              setRefreshing(false);
              fetchingNextPage = false;
            });
  }

  private void processPage(ResultPage page) {
    feedItems.addAll(page.results());
    reachedBottomOfFeed = page.isLastPage();
    view.notNull(view -> view.updateFeed(feedItems, reachedBottomOfFeed));
  }

}
