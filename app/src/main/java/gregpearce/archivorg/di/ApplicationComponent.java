package gregpearce.archivorg.di;

import dagger.Component;
import gregpearce.archivorg.domain.network.AllFeedService;
import gregpearce.archivorg.domain.network.AudioFeedService;
import gregpearce.archivorg.domain.network.BookFeedService;
import gregpearce.archivorg.domain.network.ImageFeedService;
import gregpearce.archivorg.domain.network.VideoFeedService;
import gregpearce.archivorg.network.DetailService;
import javax.inject.Singleton;

@Singleton @Component(modules = {
    ApplicationModule.class, NetworkModule.class
}) public interface ApplicationComponent {
  AllFeedService exposeAllFeedService();

  VideoFeedService exposeVideoFeedService();

  AudioFeedService exposeAudioFeedService();

  BookFeedService exposeBookFeedService();

  ImageFeedService exposeImageFeedService();

  DetailService exposeDetailService();
}
