package gregpearce.archivorg.di;

import dagger.Component;
import gregpearce.archivorg.domain.network.FeedServiceFactory;
import gregpearce.archivorg.network.DetailService;
import javax.inject.Singleton;

@Singleton @Component(modules = {
    ApplicationModule.class, NetworkModule.class
}) public interface ApplicationComponent {
  FeedServiceFactory exposeFeedServiceFactory();

  DetailService exposeDetailService();
}
