// Generated by view binder compiler. Do not edit!
package com.example.happygoaldemo.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.happygoaldemo.R;
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentComentariosDelDiaChartBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final AAChartView AAChartView;

  private FragmentComentariosDelDiaChartBinding(@NonNull FrameLayout rootView,
      @NonNull AAChartView AAChartView) {
    this.rootView = rootView;
    this.AAChartView = AAChartView;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentComentariosDelDiaChartBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentComentariosDelDiaChartBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_comentarios_del_dia_chart, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentComentariosDelDiaChartBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.AAChartView;
      AAChartView AAChartView = ViewBindings.findChildViewById(rootView, id);
      if (AAChartView == null) {
        break missingId;
      }

      return new FragmentComentariosDelDiaChartBinding((FrameLayout) rootView, AAChartView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
