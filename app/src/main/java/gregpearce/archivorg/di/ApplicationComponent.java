package gregpearce.archivorg.di;

import javax.inject.Singleton;

import dagger.Component;
import gregpearce.archivorg.network.AudioFeedService;
import gregpearce.archivorg.network.BookFeedService;
import gregpearce.archivorg.network.ImageFeedService;
import gregpearce.archivorg.network.AllFeedService;
import gregpearce.archivorg.network.VideoFeedService;

@Singleton
@Component(modules = {
    ApplicationModule.class, NetworkModule.class
})
public interface ApplicationComponent {
  AllFeedService exposePopularSearchService();

  VideoFeedService exposeVideoSearchService();

  AudioFeedService exposeAudioSearchService();

  BookFeedService exposeBookSearchService();

  ImageFeedService exposeImageSearchService();
}
