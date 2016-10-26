package gregpearce.archivorg.platform.files;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.bluelinelabs.conductor.RouterTransaction;
import gregpearce.archivorg.R;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.FeedType;
import gregpearce.archivorg.domain.search.SearchBarPresenterFactory;
import gregpearce.archivorg.platform.BaseController;
import gregpearce.archivorg.platform.feed.FeedController;
import gregpearce.archivorg.platform.util.ArSearchView;
import gregpearce.archivorg.platform.util.BundleBuilder;
import javax.inject.Inject;

public class FilesController extends BaseController {
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.recycler_view) RecyclerView recyclerView;

  private static final String ARGUMENT_ID = "ARGUMENT_ID";
  private final String id;

  public FilesController(String archiveItemId) {
    this(BundleBuilder.create()
                      .putString(ARGUMENT_ID, archiveItemId)
                      .build());
  }

  public FilesController(Bundle args) {
    super(args);
    id = args.getString(ARGUMENT_ID);
  }

  @Override protected void onCreate() {
    //getComponent().inject(this);
    setHasOptionsMenu(true);
  }

  @Override protected View inflateView(@NonNull LayoutInflater inflater,
                                       @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.controller_files, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    setActionBar(toolbar);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setTitle(R.string.files);
  }

  @Override protected void onAttach(@NonNull View view) {
    super.onAttach(view);
    getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
  }
}
