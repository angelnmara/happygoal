// Generated by view binder compiler. Do not edit!
package com.example.happygoaldemo.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.happygoaldemo.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class FragmentEstadisticaPersonalChartBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  private FragmentEstadisticaPersonalChartBinding(@NonNull FrameLayout rootView) {
    this.rootView = rootView;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentEstadisticaPersonalChartBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentEstadisticaPersonalChartBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_estadistica_personal_chart, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentEstadisticaPersonalChartBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    return new FragmentEstadisticaPersonalChartBinding((FrameLayout) rootView);
  }
}