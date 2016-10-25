package gregpearce.archivorg.platform.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.lapism.searchview.SearchView;
import gregpearce.archivorg.di.ControllerComponent;
import gregpearce.archivorg.domain.model.FeedContentType;
import gregpearce.archivorg.domain.model.MediaType;
import gregpearce.archivorg.domain.search.SearchPresenter;
import gregpearce.archivorg.platform.MainApplication;
import gregpearce.archivorg.R;

public class ArSearchView extends SearchView {
  private SearchPresenter searchPresenter;

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
    setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        searchPresenter.search(query);
        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });

  }

  public void setPresenter(SearchPresenter searchPresenter) {
    this.searchPresenter = searchPresenter;
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

    private FeedContentType contentType;
    final private ImageView icon;
    final private TextView text;

    public SearchViewHolder(View view) {
      super(view);
      icon = (ImageView) view.findViewById(
          com.lapism.searchview.R.id.imageView_item_icon_left);
      text = (TextView) view.findViewById(com.lapism.searchview.R.id.textView_item_text);
      view.setOnClickListener(this);
    }

    public void bind(int position) {
      setupContentType(position);
      setupTitle();
      setupIcon();
    }

    private void setupContentType(int position) {
      switch (position) {
        case 0:
          contentType = FeedContentType.All;
          break;
        case 1:
          contentType = FeedContentType.Audio;
          break;
        case 2:
          contentType = FeedContentType.Video;
          break;
        case 3:
          contentType = FeedContentType.Book;
          break;
        case 4:
          contentType = FeedContentType.Image;
          break;
        default:
          throw new RuntimeException();
      }
    }

    private void setupTitle() {
      text.setText(getTitle());
    }

    private @StringRes int getTitle() {
      switch (contentType) {
        case Video:
          return R.string.search_video;
        case Audio:
          return R.string.search_audio;
        case Book:
          return R.string.search_text;
        case Image:
          return R.string.search_images;
        default:
          return R.string.search_all;
      }
    }

    private void setupIcon() {
      icon.setImageResource(getIconDrawableRes());
    }

    private @DrawableRes int getIconDrawableRes() {
      switch (contentType) {
        case Video:
          return R.drawable.ic_tv_black_24dp;
        case Audio:
          return R.drawable.ic_volume_up_black_24dp;
        case Book:
          return R.drawable.ic_chrome_reader_mode_black_24dp;
        case Image:
          return R.drawable.ic_image_black_24dp;
        default:
          return R.drawable.ic_account_balance_black_24dp;
      }
    }

    @Override
    public void onClick(View v) {
      searchPresenter.searchOptionClicked(contentType, mEditText.getText().toString());
    }
  }
}