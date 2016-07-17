package gregpearce.archivorg.di;

import javax.inject.Singleton;

import dagger.Component;
import gregpearce.archivorg.network.SearchService;

@Singleton
@Component(modules = {
    ApplicationModule.class, NetworkModule.class
})
public interface ApplicationComponent {
  SearchService exposeSearchService();
}
