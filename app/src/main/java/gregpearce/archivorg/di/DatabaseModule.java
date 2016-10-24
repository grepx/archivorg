package gregpearce.archivorg.di;

import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.data.database.ItemRepositoryImpl;
import gregpearce.archivorg.domain.database.ItemRepository;
import javax.inject.Singleton;

@Module public class DatabaseModule {
  @Provides @Singleton
  ItemRepository provideBookmarkRepository(ItemRepositoryImpl bookmarkRepository) {
    return bookmarkRepository;
  }
}
