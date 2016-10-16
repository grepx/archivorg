package gregpearce.archivorg.network;

import gregpearce.archivorg.domain.model.ArchiveFile;
import gregpearce.archivorg.domain.model.ArchiveItem;
import gregpearce.archivorg.util.NullUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

@Singleton public class DetailService {
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
                  files.add(ArchiveFile.builder()
                                       .name(file.name)
                                       .size(file.size)
                                       .length(file.length)
                                       .source(file.source)
                                       .format(file.format)
                                       .md5(file.md5)
                                       .build());
                }

                ItemResponse.Metadata metadata = response.metadata;

                // links in description HTML are mangled - manually fix them
                String description = NullUtil.defaultValue(metadata.description).replace("//web.archive.org", "https://web.archive.org");

                return ArchiveItem.builder()
                                  .id(id)
                                  .title(NullUtil.defaultValue(metadata.title))
                                  .description(description)
                                  .publishedDate(ArchiveOrgUtil.parseDateApiV2(metadata.publicdate))
                                  .mediaType(ArchiveOrgUtil.parseMediaType(metadata.mediatype,
                                                                           metadata.type))
                                  .creator(NullUtil.defaultValue(metadata.creator))
                                  .uploader(NullUtil.defaultValue(metadata.uploader))
                                  .files(files)
                                  .isBookmarked(false)
                                  .build();
              });
  }
}
