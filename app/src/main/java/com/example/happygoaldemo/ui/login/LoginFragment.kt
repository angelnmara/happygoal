package com.example.happygoaldemo.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.happygoaldemo.MainActivity
import com.example.happygoaldemo.R
import com.example.happygoaldemo.api.RestApiService
import com.example.happygoaldemo.data.model.Login
import com.example.happygoaldemo.databinding.FragmentLoginBinding
import com.example.happygoaldemo.tools.Tools
import com.google.android.material.snackbar.Snackbar
import java.util.*


class LoginFragment : Fragment() {

    private val TAG = javaClass.name
    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    val tools = Tools()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root

    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tools.ocultaToolBar(activity)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.login
        val loadingProgressBar = binding.loading

        loginButton.isEnabled = false
        //loginButton.isClickable = false
        //loginButton.setBackgroundResource(R.drawable.btn_login_disabled)

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
                Observer { loginFormState ->
                    if (loginFormState == null) {
                        return@Observer
                    }
                    loginButton.isEnabled = loginFormState.isDataValid
                    /*if(loginButton.isEnabled){
                        loginButton.setBackgroundResource(R.drawable.btn_login)
                    }else{
                        loginButton.setBackgroundResource(R.drawable.btn_login_disabled)
                    }*/
                    loginFormState.usernameError?.let {
                        usernameEditText.error = getString(it)
                    }
                    loginFormState.passwordError?.let {
                        passwordEditText.error = getString(it)
                    }
                })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
                Observer { loginResult ->
                    loginResult ?: return@Observer
                    loadingProgressBar.visibility = View.GONE
                    loginResult.error?.let {
                        showLoginFailed(it)
                    }
                    loginResult.success?.let {
                        updateUiWithUser(it)
                    }
                })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                        usernameEditText.text.toString(),
                        passwordEditText.text.toString()
                )
            }
            false
        }

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            loginViewModel.login(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
            )

            addDummyUser(view, usernameEditText.text.toString(), passwordEditText.text.toString())

        }
    }

    fun addDummyUser(view: View, userName: String, pass: String) {
        val apiService = RestApiService()
        val login = Login(username = userName,
                password = pass)

        apiService.loginFun(login) {
            if (it?.codeHttp == 200) {
                tools.savePreferences(requireActivity(), getString(R.string.token), it.token, 1)
                tools.savePreferences(requireActivity(), getString(R.string.isloged), "true", 2)
                tools.savePreferences(requireActivity(), getString(R.string.dateloged), (Calendar.getInstance().timeInMillis + getString(R.string.timeExpiration).toLong()).toString(), 3)
                tools.savePreferences(requireActivity(), getString(R.string.username), userName, 1 )

                apiService.detalleUsuario(userName, it.token){
                    tools.savePreferences(requireActivity(), getString(R.string.idempresa), it?.idEmpresa.toString(), 3 )
                    tools.savePreferences(requireActivity(), getString(R.string.idusuario), it?.idUsuario.toString(), 3 )
                    val action = LoginFragmentDirections.actionLoginFragmentToTestFragment()
                    view.findNavController().navigate(action)
                    (requireActivity() as MainActivity).supportActionBar!!.show()
                }
            } else {
                //Timber.d("Error registering new user")
                //Toast.makeText(context, resources.getText(R.string.msjNoSeEncuentraRegistrado), Toast.LENGTH_LONG).show()
                Snackbar.make(view, resources.getText(R.string.msjNoSeEncuentraRegistrado), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /*private fun savePreferences(token:String, userName: String, idEmpresa:Long?, idUsuario: Long?){
        tools.savePreferences(requireActivity(), getString(R.string.token), token, 1)
        tools.savePreferences(requireActivity(), getString(R.string.isloged), "true", 2)
        tools.savePreferences(requireActivity(), getString(R.string.dateloged), (Calendar.getInstance().timeInMillis + getString(R.string.timeExpiration).toLong()).toString(), 3)
        tools.savePreferences(requireActivity(), getString(R.string.username), userName, 1 )
        tools.savePreferences(requireActivity(), getString(R.string.idempresa), userName, 3 )
        tools.savePreferences(requireActivity(), getString(R.string.idusuario), userName, 3 )
    }*/

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        // TODO : initiate successful logged in experience
        //val appContext = context?.applicationContext ?: return
        //Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
        //view?.let { Snackbar.make(it, welcome, Snackbar.LENGTH_SHORT).show() }
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        //val appContext = context?.applicationContext ?: return
        //Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
        view?.let { Snackbar.make(it, errorString, Snackbar.LENGTH_SHORT).show() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}