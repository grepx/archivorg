package gregpearce.archivorg.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gregpearce.archivorg.R;

public class LatestFragment extends Fragment {

  public LatestFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_audio, container, false);
    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
    textView.setText(getString(R.string.section_format, 1));
    return rootView;
  }
}
