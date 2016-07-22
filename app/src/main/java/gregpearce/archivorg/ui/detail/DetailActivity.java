package gregpearce.archivorg.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import gregpearce.archivorg.R;
import gregpearce.archivorg.ui.BaseActivity;

public class DetailActivity extends BaseActivity {

  private static String INTENT_EXTRA_ID = "INTENT_EXTRA_ID";

  public static Intent getCallingIntent(Context context, String id) {
    Intent intent = new Intent(context, DetailActivity.class);
    intent.putExtra(INTENT_EXTRA_ID, id);
    return intent;
  }

  @Override  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle("");
  }
}
