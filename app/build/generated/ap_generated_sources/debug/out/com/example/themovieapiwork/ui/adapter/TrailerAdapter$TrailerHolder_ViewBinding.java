// Generated code from Butter Knife. Do not modify!
package com.example.themovieapiwork.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.themovieapiwork.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TrailerAdapter$TrailerHolder_ViewBinding implements Unbinder {
  private TrailerAdapter.TrailerHolder target;

  @UiThread
  public TrailerAdapter$TrailerHolder_ViewBinding(TrailerAdapter.TrailerHolder target,
      View source) {
    this.target = target;

    target.trailerView = Utils.findRequiredViewAsType(source, R.id.trailer_IV, "field 'trailerView'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TrailerAdapter.TrailerHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.trailerView = null;
  }
}
