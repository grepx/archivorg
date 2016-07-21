package gregpearce.archivorg.ui.feed;

import java.util.ArrayList;
import java.util.List;

import gregpearce.archivorg.model.ArchiveEntity;
import gregpearce.archivorg.model.ResultPage;
import gregpearce.archivorg.network.SearchService;
import gregpearce.archivorg.ui.BasePresenter;
import gregpearce.archivorg.util.RxUtil;
import timber.log.Timber;

public class FeedPresenter extends BasePresenter<FeedView> {

  SearchService searchService;

  private String query = "";
  private boolean queryInitialized = false;
  private boolean refreshing = false;
  private final List<FeedItem> feedItems = new ArrayList<>();
  private int currentPage = 0;
  private boolean fetchingNextPage = false;
  private boolean reachedBottomOfFeed = false;
  private boolean hasFocus = false;
  private boolean resultsNeedUpdating = false;

  public FeedPresenter(SearchService searchService) {
    this.searchService = searchService;
  }

  public void onFocus() {
    hasFocus = true;
    if (resultsNeedUpdating) {
      updateResults();
      resultsNeedUpdating = false;
    } else if (queryInitialized) { // check that first query has been performed before updating view
      updateViewFeedItems();
    }
  }

  public void onLooseFocus() {
    hasFocus = false;
  }

  public void search(String query) {
    if (queryInitialized && this.query.equals(query)) {
      // prevent unnecessary data loss and network calls
      return;
    }
    queryInitialized = true;
    this.query = query;
    if (hasFocus) {
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
    searchService.search(query, currentPage)
        .compose(RxUtil.subscribeDefaults())
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
              view.notNull(view -> view.showError("Failed to load content.\nPlease check your network settings and try again."));
              setRefreshing(false);
              fetchingNextPage = false;
            });
  }

  private void processPage(ResultPage page) {
    for (ArchiveEntity archiveEntity : page.results()) {
      feedItems.add(FeedItem.create(archiveEntity.title(),
                                    archiveEntity.description(),
                                    archiveEntity.publishedDate(),
                                    archiveEntity.mediaType()));
    }
    reachedBottomOfFeed = page.isLastPage();
    updateViewFeedItems();
  }

  private void updateViewFeedItems() {
    view.notNull(view -> view.updateFeed(feedItems, reachedBottomOfFeed));
  }
}
