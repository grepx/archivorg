package gregpearce.archivorg.ui.discover;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.support.ControllerPagerAdapter;
import gregpearce.archivorg.ui.feed.FeedController;
import gregpearce.archivorg.ui.feed.FeedType;

public class DiscoverPagerAdapter extends ControllerPagerAdapter {

  public DiscoverPagerAdapter(Controller host) {
    super(host, false);
  }

  @Override public Controller getItem(int position) {
    switch (position) {
      case 0:
        return new FeedController(FeedType.All);
      case 1:
        return new FeedController(FeedType.Audio);
      case 2:
        return new FeedController(FeedType.Video);
      case 3:
        return new FeedController(FeedType.Book);
      case 4:
        return new FeedController(FeedType.Image);
    }
    throw new RuntimeException();
  }

  @Override public int getCount() {
    return 5;
  }

  @Override public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0:
        return "ALL";
      case 1:
        return "AUDIO";
      case 2:
        return "VIDEO";
      case 3:
        return "TEXT";
      case 4:
        return "IMAGES";
    }
    throw new RuntimeException();
  }
}

