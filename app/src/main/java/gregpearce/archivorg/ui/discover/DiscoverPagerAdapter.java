package gregpearce.archivorg.ui.discover;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import gregpearce.archivorg.ui.feed.FeedFragment;
import gregpearce.archivorg.ui.feed.FeedType;

public class DiscoverPagerAdapter extends FragmentPagerAdapter {

  public DiscoverPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return FeedFragment.newInstance(FeedType.All);
      case 1:
        return FeedFragment.newInstance(FeedType.Audio);
      case 2:
        return  FeedFragment.newInstance(FeedType.Video);
      case 3:
        return  FeedFragment.newInstance(FeedType.Book);
      case 4:
        return  FeedFragment.newInstance(FeedType.Image);
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

