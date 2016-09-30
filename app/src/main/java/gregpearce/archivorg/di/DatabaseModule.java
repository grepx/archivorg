package gregpearce.archivorg.di;

import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.database.BookmarkRepositoryImpl;
import gregpearce.archivorg.domain.database.ItemRepository;
import javax.inject.Singleton;

@Module public class DatabaseModule {
  @Provides @Singleton
  ItemRepository provideBookmarkRepository(BookmarkRepositoryImpl bookmarkRepository) {
    return bookmarkRepository;
  }
}
