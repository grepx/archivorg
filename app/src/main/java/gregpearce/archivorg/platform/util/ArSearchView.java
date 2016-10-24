package gregpearce.archivorg.platform.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.lapism.searchview.SearchView;
import gregpearce.archivorg.platform.MainApplication;
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
    setAdapter(new SearchAdapter());
  }

  private class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      final View view = inflater.inflate(com.lapism.searchview.R.layout.search_item, parent, false);
      return new SearchViewHolder(view);
    }

    @Override public void onBindViewHolder(SearchViewHolder holder, int position) {
      holder.bind(position);
    }

    @Override public int getItemCount() {
      return 5;
    }
  }

  private class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    final protected ImageView icon;
    final protected TextView text;

    public SearchViewHolder(View view) {
      super(view);
      icon = (ImageView) view.findViewById(
          com.lapism.searchview.R.id.imageView_item_icon_left);
      text = (TextView) view.findViewById(com.lapism.searchview.R.id.textView_item_text);
      view.setOnClickListener(this);
    }

    public void bind(int position) {
      setupTitle(position);
      setupIcon(position);
    }

    private void setupTitle(int position) {
      switch (position) {
        case 0:
          text.setText(R.string.all);
          break;
        case 1:
          text.setText(R.string.audio);
          break;
        case 2:
          text.setText(R.string.video);
          break;
        case 3:
          text.setText(R.string.text);
          break;
        case 4:
          text.setText(R.string.images);
          break;
        default:
          throw new RuntimeException();
      }
    }

    private void setupIcon(int position) {
      switch (position) {
        case 0:
          icon.setImageResource(R.drawable.ic_account_balance_black_24dp);
          break;
        case 1:
          icon.setImageResource(R.drawable.ic_volume_up_black_24dp);
          break;
        case 2:
          icon.setImageResource(R.drawable.ic_tv_black_24dp);
          break;
        case 3:
          icon.setImageResource(R.drawable.ic_chrome_reader_mode_black_24dp);
          break;
        case 4:
          icon.setImageResource(R.drawable.ic_image_black_24dp);
          break;
        default:
          throw new RuntimeException();
      }
    }

    @Override
    public void onClick(View v) {
    }
  }
}