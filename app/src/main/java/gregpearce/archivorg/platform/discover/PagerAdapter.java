package gregpearce.archivorg.platform.discover;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.support.ControllerPagerAdapter;
import gregpearce.archivorg.domain.model.FeedContentType;

class PagerAdapter extends ControllerPagerAdapter {

  private final FeedTab[] tabs;

  public PagerAdapter(Controller host, FeedTab[] tabs) {
    super(host, false);
    this.tabs = tabs;
  }

  @Override public Controller getItem(int position) {
    return tabs[position].controller;
  }

  @Override public int getCount() {
    return tabs.length;
  }

  @Override public CharSequence getPageTitle(int position) {
    return tabs[position].title;
  }

  public FeedContentType getFeedType(int position) {
    return tabs[position].feedContentType;
  }

  public int getFeedPosition(FeedContentType feedContentType) {
    for (int i = 0; i < tabs.length; i++) {
      if (tabs[i].feedContentType == feedContentType) {
        return i;
      }
    }
    throw new RuntimeException();
  }
}

