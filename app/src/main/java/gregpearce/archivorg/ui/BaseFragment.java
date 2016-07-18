package gregpearce.archivorg.ui;

import android.support.v4.app.Fragment;

import gregpearce.archivorg.di.ActivityComponent;

public abstract class BaseFragment extends Fragment {
  ActivityComponent activityComponent;

  protected final ActivityComponent getComponent() {
    if (activityComponent == null) {
      activityComponent = ((BaseActivity)getActivity()).getComponent();
    }
    return activityComponent;
  }
}
