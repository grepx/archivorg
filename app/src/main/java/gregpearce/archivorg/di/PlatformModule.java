package gregpearce.archivorg.di;

import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.domain.detail.LinkSharer;
import gregpearce.archivorg.platform.LinkSharerImpl;

@Module public class PlatformModule {
  @Provides LinkSharer provideLinkSharer(LinkSharerImpl linkSharer) {
    return linkSharer;
  }
}
