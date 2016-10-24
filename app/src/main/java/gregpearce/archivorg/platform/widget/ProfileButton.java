package gregpearce.archivorg.platform.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import gregpearce.archivorg.R;
import timber.log.Timber;

public class ProfileButton extends FrameLayout {

  @BindView(R.id.image) ImageView imageView;
  @BindView(R.id.text) TextView textView;
  private ColorStateList tint;

  public ProfileButton(Context context) {
    super(context);
    init(context, null);
  }

  public ProfileButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public ProfileButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    inflate(context, R.layout.button_profile, this);
    ButterKnife.bind(this);

    setClickable(true);

    TypedArray attributes = context.getTheme()
                                   .obtainStyledAttributes(attrs, R.styleable.ProfileButton, 0, 0);

    // try parsing settings from custom xml attributes
    String text = null;
    @DrawableRes int image = -1;
    tint = null;

    try {
      text = attributes.getString(R.styleable.ProfileButton_text);
      image = attributes.getResourceId(R.styleable.ProfileButton_src, -1);
      tint = attributes.getColorStateList(R.styleable.ProfileButton_tint);
    } catch (Exception e) {
      Timber.e(e, "Failed to parse HeaderButton arguments");
    } finally {
      attributes.recycle();
    }

    if (image >= 0) {
      setImage(image);
    }
    if (text != null && !text.isEmpty()) {
      setText(text);
    }
    if (tint != null) {
      setTintList(tint);
    }
  }

  public void setImage(@DrawableRes int image) {
    Drawable drawable = VectorDrawableCompat.create(getResources(), image, null);
    setImage(drawable);
  }

  public void setImage(Drawable drawable) {
    imageView.setImageDrawable(drawable);
    if (tint != null) {
      setTintList(tint);
    }
  }

  public void setText(@StringRes int text) {
    textView.setText(text);
  }

  public void setText(String text) {
    textView.setText(text);
  }

  public void setTintList(@ColorRes int colorId) {
    setTintList(getResources().getColorStateList(colorId));
  }

  public void setTintList(ColorStateList color) {
    tint = color;
    Drawable drawable = imageView.getDrawable();
    if (drawable != null) {
      drawable = DrawableCompat.wrap(drawable);
      DrawableCompat.setTintList(drawable, color);
    }
    textView.setTextColor(color);
  }

  @Override
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    // FRAMEWORK_BUG_FIX: https://code.google.com/p/android/issues/detail?id=172067
    imageView.invalidate();
  }
}