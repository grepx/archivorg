package gregpearce.archivorg.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ExplorePagerAdapter extends FragmentPagerAdapter {

  public ExplorePagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    return new LatestFragment();
  }

  @Override public int getCount() {
    return 5;
  }

  @Override public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0:
        return "LATEST";
      case 1:
        return "AUDIO";
      case 2:
        return "VIDEO";
      case 3:
        return "BOOKS";
      case 4:
        return "IMAGES";
    }
    return null;
  }
}

