package com.example.happygoaldemo

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.happygoaldemo.api.RestApiService
import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.databinding.FragmentTestBinding
import com.example.happygoaldemo.ui.login.LoginFragmentDirections
import com.example.happygoaldemo.ui.test.TestViewModel
import com.example.happygoaldemo.ui.test.TestViewModelFactory
import com.google.android.material.appbar.CollapsingToolbarLayout
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var testViewModel: TestViewModel
    private var _binding: FragmentTestBinding? = null
    private var comentarioMaximo: Long = 15
    private var calificacionId: Int? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun validaLogin(view:View) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = false
        val isloged = sharedPref.getBoolean(getString(R.string.isloged), defaultValue)
        var dateLoged = sharedPref.getLong(getString(R.string.dateloged), 0)
        if(!(isloged && (dateLoged > Calendar.getInstance().timeInMillis))){
            val action = TestFragmentDirections.actionTestFragmentToLoginFragment()
            view.findNavController().navigate(action)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validaLogin(view)

        testViewModel = ViewModelProvider(this, TestViewModelFactory()).get(TestViewModel::class.java)
        val lnlTest = binding.lnlTest
        val enojado = binding.imgEnojado
        val estresado = binding.imgEstresado
        val feliz = binding.imgFeliz
        val neutral = binding.imgTranquilo
        val motivado = binding.imgMotivado
        val titulo = binding.txtTitulo
        val btnComenta = binding.btnComenta
        val txtComenta = binding.txtComenta

        testViewModel.testFormState.observe(viewLifecycleOwner, Observer { testFormState ->
            if (testFormState == null) {
                return@Observer
            }
            testFormState.emocionError?.let {
                txtComenta.error = getString(it)
            }
        })

        enojado.setOnClickListener{
            titulo.setText(R.string.enojado)
            titulo.setTextColor(resources.getColor(R.color.white))
            lnlTest.setBackgroundColor(resources.getColor(R.color.enojado))
            cleanFaces()
            enojado.setImageResource(R.drawable.ic_enojado_r)
            setVisible(view)
            calificacionId = 5
        }
        feliz.setOnClickListener {
            titulo.setText(R.string.feliz)
            titulo.setTextColor(resources.getColor(R.color.black))
            lnlTest.setBackgroundColor(resources.getColor(R.color.feliz))
            cleanFaces()
            feliz.setImageResource(R.drawable.ic_feliz_r)
            setVisible(view)
            calificacionId = 1
        }
        estresado.setOnClickListener {
            titulo.setText(R.string.estresado)
            titulo.setTextColor(resources.getColor(R.color.white))
            lnlTest.setBackgroundColor(resources.getColor(R.color.estresado))
            cleanFaces()
            estresado.setImageResource(R.drawable.ic_estresado_r)
            setVisible(view)
            calificacionId = 4
        }
        neutral.setOnClickListener {
            titulo.setText(R.string.tranquilo)
            titulo.setTextColor(resources.getColor(R.color.black))
            lnlTest.setBackgroundColor(resources.getColor(R.color.tranquilo))
            cleanFaces()
            neutral.setImageResource(R.drawable.ic_tranquilo_r)
            setVisible(view)
            calificacionId = 3
        }
        motivado.setOnClickListener {
            titulo.setText(R.string.motivado)
            titulo.setTextColor(resources.getColor(R.color.black))
            lnlTest.setBackgroundColor(resources.getColor(R.color.motivado))
            cleanFaces()
            motivado.setImageResource(R.drawable.ic_motivado_r)
            setVisible(view)
            calificacionId = 2
        }

        btnComenta.setOnClickListener {
            if(titulo.text.contains(getString(R.string.comoTeSientes))){
                Toast.makeText(context, getString(R.string.msjNoSentimiento), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(txtComenta.length()<=comentarioMaximo){
                Toast.makeText(context, getString(R.string.msjNoComentario), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val comenta = titulo.text.toString()

                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
                    val defaultValue = ""
                    val token = sharedPref.getString(getString(R.string.token), defaultValue)

            val apiService = RestApiService()
            val calificacion = Calificacion(
                    idCalificacion = null,
                    calificacion = calificacionId,
                    emocion = txtComenta.text.toString(),
                    idUsuario = 1
            );
            apiService.calificacionFun(calificacion, token.toString()){
                if (it == 200) {
                    // it = newly added user parsed as response
                    // it?.id = newly added user ID
                    val action = TestFragmentDirections.actionTestFragmentToResponseFragment(comenta)
                    view.findNavController().navigate(action)
                } else {
                    //Timber.d("Error registering new user")
                    Toast.makeText(context, "Ocurrio un error al guardar tu emoción", Toast.LENGTH_LONG).show()
                }
            }
        }

        val afterTextChangedListener = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                testViewModel.txtEmocionChanged(
                        txtComenta.text.toString()
                )
            }
        }

        txtComenta.addTextChangedListener(afterTextChangedListener)

    }

    fun cleanFaces(){
        val enojado = binding.imgEnojado
        val estresado = binding.imgEstresado
        val feliz = binding.imgFeliz
        val neutral = binding.imgTranquilo
        val motivado = binding.imgMotivado
        enojado.setImageResource(R.drawable.ic_enojado)
        motivado.setImageResource(R.drawable.ic_motivado)
        estresado.setImageResource(R.drawable.ic_estresado)
        feliz.setImageResource(R.drawable.ic_feliz)
        neutral.setImageResource(R.drawable.ic_tranquilo)
    }

    fun setVisible(view: View){
        var lnlTxtComenta = binding.lnlTxtComenta
        var lnlBtnComenta = binding.lnlBtnComenta
        lnlTxtComenta.visibility = View.VISIBLE
        lnlBtnComenta.visibility = View.VISIBLE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TestFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}