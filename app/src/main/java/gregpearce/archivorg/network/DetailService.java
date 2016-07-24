package gregpearce.archivorg.network;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import gregpearce.archivorg.domain.model.ArchiveFile;
import gregpearce.archivorg.domain.model.ArchiveItem;
import gregpearce.archivorg.util.NullUtil;
import rx.Observable;

@Singleton
public class DetailService {
  private ArchiveOrgApiV2 api;

  @Inject DetailService(ArchiveOrgApiV2 api) {
    this.api = api;
  }

  public Observable<ArchiveItem> get(String id) {
    return api.items(id)
        // retry on network failure 3 times
        .retry(3)
        // map the network response to the domain model
        .map(response -> {
          List<ArchiveFile> files = new ArrayList<>(response.files.size());
          for (ItemResponse.File file : response.files) {
            files.add(ArchiveFile.create(file.name, file.size, file.length, file.source, file.format));
          }
          ItemResponse.Metadata metadata = response.metadata;
          return ArchiveItem.create(NullUtil.defaultValue(metadata.title),
                                    NullUtil.defaultValue(metadata.description),
                                    ArchiveOrgUtil.parseDateApiV2(metadata.publicdate),
                                    ArchiveOrgUtil.parseMediaType(metadata.mediatype, metadata.type),
                                    NullUtil.defaultValue(metadata.creator),
                                    NullUtil.defaultValue(metadata.uploader),
                                    files);
        });
  }

}
