package gregpearce.archivorg.ui.discover;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.support.ControllerPagerAdapter;
import gregpearce.archivorg.ui.feed.FeedFragment;
import gregpearce.archivorg.ui.feed.FeedType;

public class DiscoverPagerAdapter extends ControllerPagerAdapter {

  public DiscoverPagerAdapter(Controller host) {
    super(host, false);
  }

  @Override public Controller getItem(int position) {
    switch (position) {
      case 0:
        return new FeedFragment(FeedType.All);
      case 1:
        return new FeedFragment(FeedType.Audio);
      case 2:
        return new FeedFragment(FeedType.Video);
      case 3:
        return new FeedFragment(FeedType.Book);
      case 4:
        return new FeedFragment(FeedType.Image);
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

