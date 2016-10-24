package gregpearce.archivorg.platform.detail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.AttributeSet;
import gregpearce.archivorg.R;
import gregpearce.archivorg.platform.widget.ProfileButton;

public class BookmarkButton extends ProfileButton {

  public final static int STATE_NOT_BOOKMARKED = 0;
  public final static int STATE_BOOKMARKED = 1;

  private Drawable star;
  private Drawable emptyStar;

  public BookmarkButton(Context context) {
    super(context);
    init();
  }

  public BookmarkButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public BookmarkButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    star = VectorDrawableCompat.create(getResources(), R.drawable.ic_star_black_24dp,
                                       null);
    emptyStar = VectorDrawableCompat.create(getResources(), R.drawable.ic_star_border_black_24dp,
                                            null);
  }

  public void setState(int state) {
    switch (state) {
      case STATE_BOOKMARKED:
        setImage(star);
        break;
      default:
        setImage(emptyStar);
    }
  }
}
