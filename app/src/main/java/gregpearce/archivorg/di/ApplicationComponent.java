package gregpearce.archivorg.di;

import dagger.Component;
import gregpearce.archivorg.domain.database.ItemRepository;
import gregpearce.archivorg.domain.service.FeedServiceFactory;
import gregpearce.archivorg.data.network.DetailService;
import javax.inject.Singleton;

@Singleton @Component(modules = {
    ApplicationModule.class,
    NetworkModule.class,
    DatabaseModule.class,
}) public interface ApplicationComponent {
  FeedServiceFactory exposeFeedServiceFactory();

  DetailService exposeDetailService();

  ItemRepository exposeItemRepository();
}
