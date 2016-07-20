package gregpearce.archivorg.di;

import javax.inject.Singleton;

import dagger.Component;
import gregpearce.archivorg.network.AudioSearchService;
import gregpearce.archivorg.network.BookSearchService;
import gregpearce.archivorg.network.ImageSearchService;
import gregpearce.archivorg.network.PopularSearchService;
import gregpearce.archivorg.network.VideoSearchService;

@Singleton
@Component(modules = {
    ApplicationModule.class, NetworkModule.class
})
public interface ApplicationComponent {
  PopularSearchService exposePopularSearchService();

  VideoSearchService exposeVideoSearchService();

  AudioSearchService exposeAudioSearchService();

  BookSearchService exposeBookSearchService();

  ImageSearchService exposeImageSearchService();
}
