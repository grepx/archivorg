package gregpearce.archivorg.ui.discover;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.support.ControllerPagerAdapter;
import gregpearce.archivorg.ui.feed.FeedController;
import gregpearce.archivorg.ui.feed.FeedType;

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
}

