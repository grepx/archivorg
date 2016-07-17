package gregpearce.archivorg.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import gregpearce.archivorg.R;

public class ExploreActivity extends AppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.view_pager) ViewPager viewPager;
  @BindView(R.id.tab_layout) TabLayout tabLayout;

  private SectionsPagerAdapter sectionsPagerAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_explore);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);

    sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(sectionsPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  public static class PlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }

    public static PlaceholderFragment newInstance(int sectionNumber) {
      PlaceholderFragment fragment = new PlaceholderFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_audio, container, false);
      TextView textView = (TextView) rootView.findViewById(R.id.section_label);
      textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
      return rootView;
    }
  }

  public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override public Fragment getItem(int position) {
      return PlaceholderFragment.newInstance(position + 1);
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
}
