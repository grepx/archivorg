package gregpearce.archivorg.di;

import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.BuildConfig;
import gregpearce.archivorg.domain.detail.LinkSharer;
import gregpearce.archivorg.domain.network.FeedServiceFactory;
import gregpearce.archivorg.network.ArchiveOrgApiV1;
import gregpearce.archivorg.network.ArchiveOrgApiV2;
import gregpearce.archivorg.network.FeedServiceFactoryImpl;
import gregpearce.archivorg.ui.LinkSharerImpl;
import java.net.InetSocketAddress;
import java.net.Proxy;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module public class PlatformModule {
  @Provides LinkSharer provideLinkSharer(LinkSharerImpl linkSharer) {
    return linkSharer;
  }
}
