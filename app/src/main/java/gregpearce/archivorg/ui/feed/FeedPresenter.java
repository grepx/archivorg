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
      search();
      resultsNeedUpdating = false;
    } else {
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
      search();
    } else {
      resultsNeedUpdating = true;
    }
  }

  private void search() {
    feedItems.clear();
    reachedBottomOfFeed = false;
    updateViewFeedItems();
    refresh();
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

  private void refresh() {
    if (!refreshing) {
      feedItems.clear();
      currentPage = 1;
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
              setRefreshing(false);
              Timber.d("Feed refresh complete, results count: %d", result.totalCount());
              processPage(result);
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
      feedItems.add(FeedItem.create(archiveEntity.title(), archiveEntity.description()));
    }
    reachedBottomOfFeed = page.isLastPage();
    updateViewFeedItems();
  }

  private void updateViewFeedItems() {
    view.notNull(view -> view.updateFeed(feedItems, reachedBottomOfFeed));
  }
}
