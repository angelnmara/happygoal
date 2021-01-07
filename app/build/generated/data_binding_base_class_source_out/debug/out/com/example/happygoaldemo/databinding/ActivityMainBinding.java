// Generated by view binder compiler. Do not edit!
package com.example.happygoaldemo.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewbinding.ViewBinding;
import com.example.happygoaldemo.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final DrawerLayout rootView;

  @NonNull
  public final AppBarLayout appBarLayout;

  @NonNull
  public final CollapsingToolbarLayout collapsingToolbarLayout;

  @NonNull
  public final DrawerLayout drawerLayout;

  @NonNull
  public final FragmentContainerView navHostFragment;

  @NonNull
  public final NavigationView navView;

  @NonNull
  public final Toolbar toolbar;

  private ActivityMainBinding(@NonNull DrawerLayout rootView, @NonNull AppBarLayout appBarLayout,
      @NonNull CollapsingToolbarLayout collapsingToolbarLayout, @NonNull DrawerLayout drawerLayout,
      @NonNull FragmentContainerView navHostFragment, @NonNull NavigationView navView,
      @NonNull Toolbar toolbar) {
    this.rootView = rootView;
    this.appBarLayout = appBarLayout;
    this.collapsingToolbarLayout = collapsingToolbarLayout;
    this.drawerLayout = drawerLayout;
    this.navHostFragment = navHostFragment;
    this.navView = navView;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public DrawerLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.app_bar_layout;
      AppBarLayout appBarLayout = rootView.findViewById(id);
      if (appBarLayout == null) {
        break missingId;
      }

      id = R.id.collapsing_toolbar_layout;
      CollapsingToolbarLayout collapsingToolbarLayout = rootView.findViewById(id);
      if (collapsingToolbarLayout == null) {
        break missingId;
      }

      DrawerLayout drawerLayout = (DrawerLayout) rootView;

      id = R.id.nav_host_fragment;
      FragmentContainerView navHostFragment = rootView.findViewById(id);
      if (navHostFragment == null) {
        break missingId;
      }

      id = R.id.nav_view;
      NavigationView navView = rootView.findViewById(id);
      if (navView == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = rootView.findViewById(id);
      if (toolbar == null) {
        break missingId;
      }

      return new ActivityMainBinding((DrawerLayout) rootView, appBarLayout, collapsingToolbarLayout,
          drawerLayout, navHostFragment, navView, toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
