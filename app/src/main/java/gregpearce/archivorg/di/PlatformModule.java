package gregpearce.archivorg.di;

import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.domain.detail.LinkSharer;
import gregpearce.archivorg.domain.service.FeedServiceFactory;
import gregpearce.archivorg.ui.LinkSharerImpl;

@Module public class PlatformModule {
  @Provides LinkSharer provideLinkSharer(LinkSharerImpl linkSharer) {
    return linkSharer;
  }
}
