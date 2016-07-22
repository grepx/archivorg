package gregpearce.archivorg.ui.feed;

import javax.inject.Inject;

import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.network.AllFeedService;
import gregpearce.archivorg.network.AudioFeedService;
import gregpearce.archivorg.network.BookFeedService;
import gregpearce.archivorg.network.ImageFeedService;
import gregpearce.archivorg.network.VideoFeedService;

@ActivityScope
public class FeedPresenterFactory {

  private AllFeedService popularSearchService;
  private VideoFeedService videoSearchService;
  private AudioFeedService audioSearchService;
  private BookFeedService bookSearchService;
  private ImageFeedService imageSearchService;

  @Inject public FeedPresenterFactory(AllFeedService popularSearchService, VideoFeedService videoSearchService,
                                      AudioFeedService audioSearchService, BookFeedService bookSearchService,
                                      ImageFeedService imageSearchService) {
    this.popularSearchService = popularSearchService;
    this.videoSearchService = videoSearchService;
    this.audioSearchService = audioSearchService;
    this.bookSearchService = bookSearchService;
    this.imageSearchService = imageSearchService;
  }

  FeedPresenter popular;
  FeedPresenter video;
  FeedPresenter audio;
  FeedPresenter book;
  FeedPresenter image;

  public FeedPresenter get(FeedType type) {
    // Checks if this feed presenter has been initialized yet.
    // If it hasn't, initializes it and stores it for future calls
    switch (type) {
      case Popular:
        if (popular == null) {
          popular = new FeedPresenter(popularSearchService);
        }
        return popular;
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
