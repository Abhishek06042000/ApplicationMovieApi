// Generated code from Butter Knife. Do not modify!
package com.example.themovieapiwork.ui.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.themovieapiwork.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReviewAdapter$ReviewViewHolder_ViewBinding implements Unbinder {
  private ReviewAdapter.ReviewViewHolder target;

  @UiThread
  public ReviewAdapter$ReviewViewHolder_ViewBinding(ReviewAdapter.ReviewViewHolder target,
      View source) {
    this.target = target;

    target.authorView = Utils.findRequiredViewAsType(source, R.id.author, "field 'authorView'", TextView.class);
    target.contentView = Utils.findRequiredViewAsType(source, R.id.content, "field 'contentView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ReviewAdapter.ReviewViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.authorView = null;
    target.contentView = null;
  }
}
