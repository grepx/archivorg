package gregpearce.archivorg.ui.discover;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.support.ControllerPagerAdapter;
import gregpearce.archivorg.domain.model.FeedType;

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

  public FeedType getFeedType(int position) {
    return tabs[position].feedType;
  }

  public int getFeedPosition(FeedType feedType) {
    for (int i = 0; i < tabs.length; i++) {
      if (tabs[i].feedType == feedType) {
        return i;
      }
    }
    throw new RuntimeException();
  }
}

