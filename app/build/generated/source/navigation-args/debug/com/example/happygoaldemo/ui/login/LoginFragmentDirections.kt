package com.example.happygoaldemo.ui.login

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.happygoaldemo.R

public class LoginFragmentDirections private constructor() {
  public companion object {
    public fun actionLoginFragmentToTestFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_loginFragment_to_testFragment)
  }
}
