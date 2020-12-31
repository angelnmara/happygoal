package com.example.happygoaldemo

import android.os.Bundle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class ResponseFragmentArgs(
  public val calificacion: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("calificacion", this.calificacion)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): ResponseFragmentArgs {
      bundle.setClassLoader(ResponseFragmentArgs::class.java.classLoader)
      val __calificacion : String?
      if (bundle.containsKey("calificacion")) {
        __calificacion = bundle.getString("calificacion")
        if (__calificacion == null) {
          throw IllegalArgumentException("Argument \"calificacion\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"calificacion\" is missing and does not have an android:defaultValue")
      }
      return ResponseFragmentArgs(__calificacion)
    }
  }
}
