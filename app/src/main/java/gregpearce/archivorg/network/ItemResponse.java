package gregpearce.archivorg.network;

import java.util.List;

class ItemResponse {

  List<File> files;

  class File {
    String name;
    String size;
    String length;
    String source;
    String format;
  }

  Metadata metadata;

  class Metadata {
    String identifier;
    String title;
    String description;
    String publicdate;
    String mediatype;
    String creator;
    String uploader;
    String type;
  }
}
