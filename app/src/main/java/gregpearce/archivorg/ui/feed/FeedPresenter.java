package gregpearce.archivorg.ui.feed;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.model.ArchiveEntity;
import gregpearce.archivorg.model.ResultPage;
import gregpearce.archivorg.network.PopularSearchService;
import gregpearce.archivorg.ui.BasePresenter;
import gregpearce.archivorg.util.RxUtil;
import timber.log.Timber;

@ActivityScope
public class FeedPresenter extends BasePresenter<FeedView> {

  @Inject PopularSearchService popularSearchService;

  private String query = "";
  private boolean refreshing = false;
  private final List<FeedItem> feedItems = new ArrayList<>();
  private int currentPage = 0;
  private boolean fetchingNextPage = false;
  private boolean reachedBottomOfFeed = false;

  @Inject public FeedPresenter() {
  }

  public void search(String query) {
    this.query = query;
    feedItems.clear();
    reachedBottomOfFeed = false;
    view.updateFeed(feedItems, reachedBottomOfFeed);
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

  public void refresh() {
    if (!refreshing) {
      feedItems.clear();
      currentPage = 1;
      setRefreshing(true);
      fetchPage();
    }
  }

  private void setRefreshing(boolean refreshing) {
    this.refreshing = refreshing;
    view.updateRefreshing(this.refreshing);
  }

  private void fetchPage() {
    fetchingNextPage = true;
    popularSearchService.search(query, currentPage)
        .compose(RxUtil.subscribeDefaults())
        .subscribe(
            result -> {
              setRefreshing(false);
              Timber.d("Feed refresh complete, results count: %d", result.totalCount());
              processPage(result);
              fetchingNextPage = false;
            },
            error -> {
              view.showError("Failed to load content.\nPlease check your network settings and try again.");
              setRefreshing(false);
              fetchingNextPage = false;
            });
  }

  private void processPage(ResultPage page) {
    for (ArchiveEntity archiveEntity : page.results()) {
      feedItems.add(FeedItem.create(archiveEntity.title(), archiveEntity.description()));
    }
    reachedBottomOfFeed = page.isLastPage();
    view.updateFeed(feedItems, reachedBottomOfFeed);
  }
}
