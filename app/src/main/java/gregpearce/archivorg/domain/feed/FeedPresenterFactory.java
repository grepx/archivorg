package gregpearce.archivorg.domain.feed;

import gregpearce.archivorg.di.annotations.ControllerScope;
import gregpearce.archivorg.domain.network.AllFeedService;
import gregpearce.archivorg.domain.network.AudioFeedService;
import gregpearce.archivorg.domain.network.BookFeedService;
import gregpearce.archivorg.domain.network.ImageFeedService;
import gregpearce.archivorg.domain.network.VideoFeedService;
import gregpearce.archivorg.ui.feed.FeedType;
import javax.inject.Inject;

@ControllerScope public class FeedPresenterFactory {

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
          all.start();
        }
        return all;
      case Video:
        if (video == null) {
          video = new FeedPresenter(videoSearchService);
          video.start();
        }
        return video;
      case Audio:
        if (audio == null) {
          audio = new FeedPresenter(audioSearchService);
          audio.start();
        }
        return audio;
      case Book:
        if (book == null) {
          book = new FeedPresenter(bookSearchService);
          book.start();
        }
        return book;
      case Image:
        if (image == null) {
          image = new FeedPresenter(imageSearchService);
          image.start();
        }
        return image;
      default:
        throw new RuntimeException("Invalid feed type");
    }
  }
}
