package gregpearce.archivorg.ui.discover;

import gregpearce.archivorg.ui.feed.FeedController;
import gregpearce.archivorg.domain.model.FeedType;

class FeedTab {
  FeedController controller;
  String title;
  FeedType feedType;

  FeedTab(String title, FeedType feedType, FeedController controller) {
    this.controller = controller;
    this.title = title;
    this.feedType = feedType;
  }
}