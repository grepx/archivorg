package gregpearce.archivorg.domain.feed;

import javax.inject.Inject;

import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.domain.network.AllFeedService;
import gregpearce.archivorg.domain.network.AudioFeedService;
import gregpearce.archivorg.domain.network.BookFeedService;
import gregpearce.archivorg.domain.network.ImageFeedService;
import gregpearce.archivorg.domain.network.VideoFeedService;
import gregpearce.archivorg.ui.feed.FeedType;

@ActivityScope
public class FeedPresenterFactory {

  @Inject AllFeedService allSearchService;
  @Inject VideoFeedService videoSearchService;
  @Inject AudioFeedService audioSearchService;
  @Inject BookFeedService bookSearchService;
  @Inject ImageFeedService imageSearchService;

  @Inject public FeedPresenterFactory() {
  }

  FeedPresenter all;
  FeedPresenter video;
  FeedPresenter audio;
  FeedPresenter book;
  FeedPresenter image;

  public FeedPresenter get(FeedType type) {
    // Checks if this feed presenter has been initialized yet.
    // If it hasn't, initializes it and stores it for future calls
    switch (type) {
      case All:
        if (all == null) {
          all = new FeedPresenter(allSearchService);
        }
        return all;
      case Video:
        if (video == null) {
          video = new FeedPresenter(videoSearchService);
        }
        return video;
      case Audio:
        if (audio == null) {
          audio = new FeedPresenter(audioSearchService);
        }
        return audio;
      case Book:
        if (book == null) {
          book = new FeedPresenter(bookSearchService);
        }
        return book;
      case Image:
        if (image == null) {
          image = new FeedPresenter(imageSearchService);
        }
        return image;
      default:
        throw new RuntimeException("Invalid feed type");
    }
  }
}
