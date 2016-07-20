package gregpearce.archivorg.ui.feed;

import javax.inject.Inject;

import gregpearce.archivorg.di.annotations.ActivityScope;
import gregpearce.archivorg.network.AudioSearchService;
import gregpearce.archivorg.network.BookSearchService;
import gregpearce.archivorg.network.ImageSearchService;
import gregpearce.archivorg.network.PopularSearchService;
import gregpearce.archivorg.network.VideoSearchService;

@ActivityScope
public class FeedPresenterFactory {

  private PopularSearchService popularSearchService;
  private VideoSearchService videoSearchService;
  private AudioSearchService audioSearchService;
  private BookSearchService bookSearchService;
  private ImageSearchService imageSearchService;

  @Inject public FeedPresenterFactory(PopularSearchService popularSearchService, VideoSearchService videoSearchService,
                                      AudioSearchService audioSearchService, BookSearchService bookSearchService,
                                      ImageSearchService imageSearchService) {
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
