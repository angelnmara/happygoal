package com.example.happygoaldemo.data

import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import com.example.happygoaldemo.MainActivity
import com.example.happygoaldemo.R
import com.example.happygoaldemo.api.RestApiService
import com.example.happygoaldemo.data.model.LoggedInUser
import com.example.happygoaldemo.data.model.Login
import com.example.happygoaldemo.data.model.UserDetailResponse
import com.example.happygoaldemo.ui.login.LoginFragmentDirections
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.util.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    private lateinit var loggedInUser:LoggedInUser
    private lateinit var userDetailResponse:UserDetailResponse
    private lateinit var usernameG:String
    private val TAG = javaClass.name

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            var loggedInUser:LoggedInUser = addDummyUser(username, password)
            return Result.Success(loggedInUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    suspend fun detailsUser(username: String, token: String): UserDetailResponse {
        val apiService = RestApiService()
        apiService.getUserDetails(username, token){
            if (it?.codeHttp == 200) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                //loggedInUser = LoggedInUser(java.util.UUID.randomUUID().toString(), userName, it.token, Date(), true)
                userDetailResponse = UserDetailResponse(
                    codeHttp = it.codeHttp,
                    usuarioCreacion = 1,
                    fechaCreacion = Date(),
                    usuarioActualizacion = 1,
                    fechaActualizacion = Date(),
                    idUsuarioDetalle = 1,
                    idUsuario = 1,
                    idEmpresa = 1,
                    idArea = 1,
                    idRol = 1
                )
            } else {
                //Timber.d("Error registering new user")
                //Toast.makeText(context, resources.getText(R.string.msjNoSeEncuentraRegistrado), Toast.LENGTH_LONG).show()
                Log.d(TAG, "addDummyUser: loggin failed")
            }
        }
        return userDetailResponse
    }

    fun logout() {
        // TODO: revoke authentication
    }

    suspend fun addDummyUser(userName: String, pass: String):LoggedInUser {
        usernameG = userName
        val apiService = RestApiService()
        val login = Login(username = userName,
            password = pass)

        apiService.loginFun(login) {
            if (it?.codeHttp == 200) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                loggedInUser = LoggedInUser(java.util.UUID.randomUUID().toString(), userName, it.token, Date(), true)
            } else {
                //Timber.d("Error registering new user")
                //Toast.makeText(context, resources.getText(R.string.msjNoSeEncuentraRegistrado), Toast.LENGTH_LONG).show()
                Log.d(TAG, "addDummyUser: loggin failed")
            }
        }
        return loggedInUser
    }

}


/*
tools.savePreferences(activity, getString(R.string.token), it.token, 1)
tools.savePreferences(activity, getString(R.string.isloged), "true", 2)
tools.savePreferences(activity, getString(R.string.dateloged), (Calendar.getInstance().timeInMillis + getString(
R.string.timeExpiration).toLong()).toString(), 3)
tools.savePreferences(activity, getString(R.string.username), userName, 1 )
val action = LoginFragmentDirections.actionLoginFragmentToTestFragment()
view.findNavController().navigate(action)
(requireActivity() as MainActivity).supportActionBar!!.show()*/

/*
Snackbar.make(view, resources.getText(R.string.msjNoSeEncuentraRegistrado), Snackbar.LENGTH_SHORT).show()*/
