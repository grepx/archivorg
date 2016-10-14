package gregpearce.archivorg.di;

import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.MainApplication;
import gregpearce.archivorg.database.BookmarkRepositoryImpl;
import gregpearce.archivorg.domain.database.ItemRepository;
import io.realm.Realm;
import javax.inject.Singleton;

@Module public class DatabaseModule {
  @Provides @Singleton
  Realm provideRealm(MainApplication context) {
    Realm.init(context);
    Realm realm = Realm.getDefaultInstance();
    return realm;
  }

  @Provides @Singleton
  ItemRepository provideBookmarkRepository(BookmarkRepositoryImpl bookmarkRepository) {
    return bookmarkRepository;
  }
}
