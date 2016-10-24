package gregpearce.archivorg.platform.discover;

import android.support.annotation.StringRes;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.platform.feed.FeedController;

class FeedTab {
  FeedController controller;
  @StringRes int title;
  FeedContentType feedContentType;

  FeedTab(@StringRes int title, FeedContentType feedContentType, FeedController controller) {
    this.controller = controller;
    this.title = title;
    this.feedContentType = feedContentType;
  }
}