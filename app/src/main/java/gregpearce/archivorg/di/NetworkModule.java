package gregpearce.archivorg.di;

import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.domain.network.AllFeedService;
import gregpearce.archivorg.domain.network.AudioFeedService;
import gregpearce.archivorg.domain.network.BookFeedService;
import gregpearce.archivorg.domain.network.ImageFeedService;
import gregpearce.archivorg.domain.network.VideoFeedService;
import gregpearce.archivorg.network.AllFeedServiceImpl;
import gregpearce.archivorg.network.ArchiveOrgApiV1;
import gregpearce.archivorg.network.ArchiveOrgApiV2;
import gregpearce.archivorg.network.AudioFeedServiceImpl;
import gregpearce.archivorg.network.BookFeedServiceImpl;
import gregpearce.archivorg.network.ImageFeedServiceImpl;
import gregpearce.archivorg.network.VideoFeedServiceImpl;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module public class NetworkModule {
  @Provides @Singleton ArchiveOrgApiV1 provideArchiveOrgApiV1() {
    // add a custom http client to automatically add the output=json query param
    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
      HttpUrl originalUrl = chain.request().url();
      HttpUrl url = originalUrl.newBuilder().addQueryParameter("output", "json").build();

      Request.Builder requestBuilder = chain.request().newBuilder().url(url);
      Request request = requestBuilder.build();
      return chain.proceed(request);
    }).build();

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://archive.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(httpClient)
        .build();

    return retrofit.create(ArchiveOrgApiV1.class);
  }

  @Provides @Singleton ArchiveOrgApiV2 provideArchiveOrgApiV2() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.archivelab.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    return retrofit.create(ArchiveOrgApiV2.class);
  }

  // hide the implementation of each network service behind an interface
  @Provides @Singleton AllFeedService provideAllFeedService(AllFeedServiceImpl allFeedService) {
    return allFeedService;
  }

  @Provides @Singleton AudioFeedService provideAudioFeedService(
      AudioFeedServiceImpl audioFeedService) {
    return audioFeedService;
  }

  @Provides @Singleton BookFeedService provideBookFeedService(BookFeedServiceImpl bookFeedService) {
    return bookFeedService;
  }

  @Provides @Singleton ImageFeedService provideImageFeedService(
      ImageFeedServiceImpl imageFeedService) {
    return imageFeedService;
  }

  @Provides @Singleton VideoFeedService provideVideoFeedService(
      VideoFeedServiceImpl videoFeedService) {
    return videoFeedService;
  }
}