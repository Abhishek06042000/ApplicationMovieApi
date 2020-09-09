// Generated code from Butter Knife. Do not modify!
package com.example.themovieapiwork.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.themovieapiwork.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MovieAdapter$MovieViewHolder_ViewBinding implements Unbinder {
  private MovieAdapter.MovieViewHolder target;

  @UiThread
  public MovieAdapter$MovieViewHolder_ViewBinding(MovieAdapter.MovieViewHolder target,
      View source) {
    this.target = target;

    target.movieImageView = Utils.findRequiredViewAsType(source, R.id.movie_imageView, "field 'movieImageView'", ImageView.class);
    target.movieTitle = Utils.findRequiredViewAsType(source, R.id.movie_title, "field 'movieTitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MovieAdapter.MovieViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.movieImageView = null;
    target.movieTitle = null;
  }
}
