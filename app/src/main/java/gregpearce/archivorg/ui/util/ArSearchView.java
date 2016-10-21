package gregpearce.archivorg.ui.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import com.lapism.searchview.SearchView;
import gregpearce.archivorg.MainApplication;
import gregpearce.archivorg.R;

public class ArSearchView extends SearchView {
  public ArSearchView(Context context) {
    super(context);
    init();
  }

  public ArSearchView(Context context,
                      @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public ArSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public ArSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    setVersion(SearchView.VERSION_MENU_ITEM);
    setVersionMargins(SearchView.VERSION_MARGINS_MENU_ITEM);
    setTheme(SearchView.THEME_LIGHT, true);
    setTextSize(16);
    setDivider(false);
    setVoice(false);
    setAnimationDuration(SearchView.ANIMATION_DURATION);
    setShadow(true);
    setShadowColor(ContextCompat.getColor(MainApplication.instance, R.color.search_shadow_layout));
  }
}
