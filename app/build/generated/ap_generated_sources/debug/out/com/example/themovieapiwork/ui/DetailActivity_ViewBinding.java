// Generated code from Butter Knife. Do not modify!
package com.example.themovieapiwork.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.themovieapiwork.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailActivity_ViewBinding implements Unbinder {
  private DetailActivity target;

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target, View source) {
    this.target = target;

    target.titleTv = Utils.findRequiredViewAsType(source, R.id.titleTv, "field 'titleTv'", TextView.class);
    target.dateTv = Utils.findRequiredViewAsType(source, R.id.release_date, "field 'dateTv'", TextView.class);
    target.overviewTv = Utils.findRequiredViewAsType(source, R.id.overview, "field 'overviewTv'", TextView.class);
    target.backdropIv = Utils.findRequiredViewAsType(source, R.id.main_backdrop, "field 'backdropIv'", ImageView.class);
    target.posterIv = Utils.findRequiredViewAsType(source, R.id.poster, "field 'posterIv'", ImageView.class);
    target.ratingBar = Utils.findRequiredViewAsType(source, R.id.movie_rating, "field 'ratingBar'", RatingBar.class);
    target.reviewView = Utils.findRequiredViewAsType(source, R.id.review_rv, "field 'reviewView'", RecyclerView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.main_toolbar, "field 'toolbar'", Toolbar.class);
    target.trailerView = Utils.findRequiredViewAsType(source, R.id.trailer_rv, "field 'trailerView'", RecyclerView.class);
    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.detail_coordinator, "field 'coordinatorLayout'", CoordinatorLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titleTv = null;
    target.dateTv = null;
    target.overviewTv = null;
    target.backdropIv = null;
    target.posterIv = null;
    target.ratingBar = null;
    target.reviewView = null;
    target.toolbar = null;
    target.trailerView = null;
    target.coordinatorLayout = null;
  }
}
