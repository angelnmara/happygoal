package com.example.happygoaldemo.ui.test

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.happygoaldemo.R
import kotlin.Int
import kotlin.String

public class TestFragmentDirections private constructor() {
  private data class ActionTestFragmentToResponseFragment(
    public val calificacion: String
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_testFragment_to_responseFragment

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("calificacion", this.calificacion)
      return result
    }
  }

  public companion object {
    public fun actionTestFragmentToResponseFragment(calificacion: String): NavDirections =
        ActionTestFragmentToResponseFragment(calificacion)

    public fun actionTestFragmentToLoginFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_testFragment_to_loginFragment)

    public fun actionTestFragmentToAcercaDe(): NavDirections =
        ActionOnlyNavDirections(R.id.action_testFragment_to_acerca_de)

    public fun actionTestFragmentSelf(): NavDirections =
        ActionOnlyNavDirections(R.id.action_testFragment_self)

    public fun actionTestFragmentToComentariosDelDiaFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_testFragment_to_comentariosDelDiaFragment)
  }
}
