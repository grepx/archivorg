package gregpearce.archivorg.data;

import dagger.Lazy;
import gregpearce.archivorg.data.database.BookmarkFeedService;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.domain.service.FeedService;
import gregpearce.archivorg.domain.service.FeedServiceFactory;
import gregpearce.archivorg.data.network.SearchFeedServiceFactory;
import gregpearce.archivorg.data.network.TopFeedServiceFactory;
import javax.inject.Inject;

public class FeedServiceFactoryImpl implements FeedServiceFactory {

  @Inject TopFeedServiceFactory topFeedServiceFactory;
  @Inject SearchFeedServiceFactory searchFeedServiceProvider;
  @Inject Lazy<BookmarkFeedService> bookmarkFeedServiceLazy;

  @Inject public FeedServiceFactoryImpl() {
  }

  public FeedService get(FeedType feedType, FeedContentType feedContentType,
                         String query) {
    switch (feedType) {
      case Top:
        return topFeedServiceFactory.create(feedContentType);
      case Search:
        return searchFeedServiceProvider.create(feedContentType, query);
      case Bookmarks:
        return bookmarkFeedServiceLazy.get();
      case BookmarksSearch:
      case Downloads:
      case DownloadsSearch:
      default:
        throw new RuntimeException("Unknown feed type.");
    }
  }
}
