package gregpearce.archivorg.domain.feed;

import gregpearce.archivorg.domain.BasePresenter;
import gregpearce.archivorg.domain.model.FeedItem;
import gregpearce.archivorg.domain.model.ResultPage;
import gregpearce.archivorg.domain.network.FeedService;
import gregpearce.archivorg.util.RxUtil;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import timber.log.Timber;

public class FeedPresenter extends BasePresenter<FeedView> {

  FeedService feedService;

  private boolean refreshing = false;
  private final List<FeedItem> feedItems = new ArrayList<>();
  private int currentPage = 1;
  private boolean fetchingNextPage = false;
  private boolean reachedBottomOfFeed = false;

  public FeedPresenter(FeedService feedService) {
    this.feedService = feedService;
  }

  @Override public void start() {
    super.start();
    setRefreshing(true);
    fetchPage();
  }

  @Override protected void syncView(FeedView view) {
    view.updateFeed(feedItems, reachedBottomOfFeed);
    view.updateRefreshing(this.refreshing);
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
    Observable<ResultPage> serviceCall;

    serviceCall = feedService.getPage(currentPage);

    serviceCall.compose(RxUtil.subscribeDefaults()).subscribe(
        result -> {
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
