package com.example.happygoaldemo.ui.response

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.happygoaldemo.R

public class ResponseFragmentDirections private constructor() {
  public companion object {
    public fun actionResponseFragmentToEstadisticaPersonalNavGraph(): NavDirections =
        ActionOnlyNavDirections(R.id.action_responseFragment_to_estadistica_personal_nav_graph)
  }
}
