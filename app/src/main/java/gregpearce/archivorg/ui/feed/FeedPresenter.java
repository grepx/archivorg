package gregpearce.archivorg.ui.feed;

import javax.inject.Inject;

import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.model.ArchiveEntity;
import gregpearce.archivorg.network.SearchService;
import gregpearce.archivorg.ui.BasePresenter;
import gregpearce.archivorg.util.RxUtil;
import timber.log.Timber;

@ActivityScope
public class FeedPresenter extends BasePresenter<FeedView> {

  @Inject SearchService searchService;

  private boolean refreshing = false;

  @Inject public FeedPresenter() {
  }

  public void refresh() {
    if (!refreshing) {
      setRefreshing(true);

      searchService.search("test", 1)
          .compose(RxUtil.subscribeDefaults())
          .subscribe(
              result -> {
                setRefreshing(false);
                Timber.d("Feed refresh complete, results count: %d", result.totalCount());
                for (ArchiveEntity archiveEntity : result.results()) {
                  Timber.d("entity title: %s", archiveEntity.title());
                }
              },
              error -> {
                view.showError("Failed to load content.\nPlease check your network settings and try again.");
                setRefreshing(false);
              });
    }
  }

  private void setRefreshing(boolean isRefreshing) {
    refreshing = isRefreshing;
    view.setRefreshing(refreshing);
  }
}
