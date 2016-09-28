package gregpearce.archivorg.ui;

import android.content.Intent;
import com.bluelinelabs.conductor.Controller;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.detail.LinkSharer;
import javax.inject.Inject;

public class LinkSharerImpl implements LinkSharer {

  @Inject Controller controller;

  @Inject public LinkSharerImpl() {
  }

  @Override public void share(String id) {
    String title = controller.getResources().getString(R.string.share_title);
    String detailsLink = controller.getResources().getString(R.string.details_url_template, id);

    Intent sendIntent = new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.putExtra(Intent.EXTRA_TEXT, detailsLink);
    sendIntent.setType("text/plain");

    controller.getActivity().startActivity(Intent.createChooser(sendIntent, title));
  }
}
