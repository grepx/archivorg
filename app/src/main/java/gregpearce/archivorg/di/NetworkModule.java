package gregpearce.archivorg.di;

import dagger.Module;
import dagger.Provides;
import gregpearce.archivorg.BuildConfig;
import gregpearce.archivorg.data.network.ArchiveOrgApiV1;
import gregpearce.archivorg.data.network.ArchiveOrgApiV2;
import java.net.InetSocketAddress;
import java.net.Proxy;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module public class NetworkModule {
  @Provides @Singleton Proxy provideProxy() {
    // if a http proxy is defined, parse the settings and configure a Proxy
    String ipAndPort = BuildConfig.HTTP_PROXY_IP_AND_PORT;
    if (ipAndPort != null && !ipAndPort.isEmpty()) {
      String ip = ipAndPort.split(":")[0];
      String port = ipAndPort.split(":")[1];
      return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, Integer.parseInt(port)));
    }
    // otherwise, configure without a Proxy
    return Proxy.NO_PROXY;
  }

  @Provides @Singleton ArchiveOrgApiV1 provideArchiveOrgApiV1(Proxy proxy) {
    // add a custom http client to automatically add the output=json query param
    OkHttpClient httpClient = new OkHttpClient.Builder()
        .addInterceptor(chain -> {
          HttpUrl originalUrl = chain.request().url();
          HttpUrl url = originalUrl.newBuilder().addQueryParameter("output", "json").build();

          Request.Builder requestBuilder = chain.request().newBuilder().url(url);
          Request request = requestBuilder.build();
          return chain.proceed(request);
        })
        .proxy(proxy)
        .build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://archive.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(httpClient)
        .build();

    return retrofit.create(ArchiveOrgApiV1.class);
  }

  @Provides @Singleton ArchiveOrgApiV2 provideArchiveOrgApiV2(Proxy proxy) {
    OkHttpClient httpClient = new OkHttpClient.Builder().proxy(proxy).build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://api.archivelab.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(httpClient)
        .build();

    return retrofit.create(ArchiveOrgApiV2.class);
  }
}