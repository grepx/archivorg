package gregpearce.archivorg.domain.search;

import gregpearce.archivorg.di.annotations.ControllerScope;
import gregpearce.archivorg.domain.BasePresenter;
import gregpearce.archivorg.domain.BaseView;
import gregpearce.archivorg.domain.feed.FeedPresenter;
import gregpearce.archivorg.domain.feed.FeedPresenterFactory;
import gregpearce.archivorg.ui.feed.FeedType;
import javax.inject.Inject;

@ControllerScope public class SearchPresenter extends BasePresenter<BaseView> {

  private FeedPresenter popularFeedPresenter;
  private FeedPresenter videoFeedPresenter;
  private FeedPresenter audioFeedPresenter;
  private FeedPresenter bookFeedPresenter;
  private FeedPresenter imageFeedPresenter;

  @Inject public SearchPresenter(FeedPresenterFactory feedPresenterFactory) {
    popularFeedPresenter = feedPresenterFactory.get(FeedType.All);
    videoFeedPresenter = feedPresenterFactory.get(FeedType.Video);
    audioFeedPresenter = feedPresenterFactory.get(FeedType.Audio);
    imageFeedPresenter = feedPresenterFactory.get(FeedType.Book);
    bookFeedPresenter = feedPresenterFactory.get(FeedType.Image);
  }

  public void search(String query) {
    popularFeedPresenter.search(query);
    videoFeedPresenter.search(query);
    audioFeedPresenter.search(query);
    imageFeedPresenter.search(query);
    bookFeedPresenter.search(query);
  }

  public void onCloseSearch() {
    popularFeedPresenter.search("");
    videoFeedPresenter.search("");
    audioFeedPresenter.search("");
    imageFeedPresenter.search("");
    bookFeedPresenter.search("");
  }

  @Override protected void syncView(BaseView view) {
  }
}
