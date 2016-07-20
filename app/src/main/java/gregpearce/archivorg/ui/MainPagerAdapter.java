package gregpearce.archivorg.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import gregpearce.archivorg.ui.feed.FeedFragment;
import gregpearce.archivorg.ui.feed.FeedType;

public class MainPagerAdapter extends FragmentPagerAdapter {

  public MainPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return FeedFragment.newInstance(FeedType.Popular);
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
        return "POPULAR";
      case 1:
        return "AUDIO";
      case 2:
        return "VIDEO";
      case 3:
        return "BOOKS";
      case 4:
        return "IMAGES";
    }
    throw new RuntimeException();
  }
}

