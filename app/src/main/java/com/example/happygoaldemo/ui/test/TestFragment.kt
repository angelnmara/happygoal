package com.example.happygoaldemo.ui.test

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.happygoaldemo.MainActivity
import com.example.happygoaldemo.R
import com.example.happygoaldemo.api.RestApiService
import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.databinding.FragmentTestBinding
import com.example.happygoaldemo.tools.Tools
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
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
    private var userName = "";
    val tools = Tools()

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setHasOptionsMenu(true)

        //tools.muestraToolBar(activity)
        //var toolbar = activity?.findViewById<AppBarLayout>(R.id.toolbar)
        //toolbar?.setBackgroundColor(resources.getColor(R.color.black))

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun validaLogin(view:View) {
        val tools = Tools()
        //val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        //val defaultValue = false
        val isloged = tools.getDefaultsBolean(getString(R.string.isloged), context) //sharedPref.getBoolean(getString(R.string.isloged), defaultValue)
        var dateLoged = tools.getDefaultsLong(getString(R.string.dateloged), context) //sharedPref.getLong(getString(R.string.dateloged), 0)
        userName = tools.getDefaultsString(getString(R.string.username), context).toString() //sharedPref.getString(getString(R.string.username), "").toString()
        if(!(isloged && (dateLoged > Calendar.getInstance().timeInMillis))){
            (requireActivity() as MainActivity).supportActionBar!!.hide()
            val action = TestFragmentDirections.actionTestFragmentToLoginFragment()
            view.findNavController().navigate(action)
        }
    }

/*    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.happy_goal_menu, menu)
    }*/

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

        //val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

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
            //titulo.setTextColor(resources.getColor(R.color.white))
            lnlTest.setBackgroundResource(R.drawable.background_enojado)
            cleanFaces()
            enojado.setImageResource(R.drawable.ic_rojo_r)
            setVisible(view)
            calificacionId = 5
        }
        feliz.setOnClickListener {
            titulo.setText(R.string.feliz)
            //titulo.setTextColor(resources.getColor(R.color.black))
            lnlTest.setBackgroundResource(R.drawable.background_feliz)
            cleanFaces()
            feliz.setImageResource(R.drawable.ic_amarillo_r)
            setVisible(view)
            calificacionId = 1
        }
        estresado.setOnClickListener {
            titulo.setText(R.string.estresado)
            //titulo.setTextColor(resources.getColor(R.color.white))
            lnlTest.setBackgroundResource(R.drawable.backgroudn_estresado)
            cleanFaces()
            estresado.setImageResource(R.drawable.ic_naranja_r)
            setVisible(view)
            calificacionId = 4
        }
        neutral.setOnClickListener {
            titulo.setText(R.string.tranquilo)
            //titulo.setTextColor(resources.getColor(R.color.black))
            lnlTest.setBackgroundResource(R.drawable.background_tranquilo)
            cleanFaces()
            neutral.setImageResource(R.drawable.ic_azul_r)
            setVisible(view)
            calificacionId = 3
        }
        motivado.setOnClickListener {
            titulo.setText(R.string.motivado)
            //titulo.setTextColor(resources.getColor(R.color.black))
            lnlTest.setBackgroundResource(R.drawable.background_motivado)
            cleanFaces()
            motivado.setImageResource(R.drawable.ic_verde_r)
            setVisible(view)
            calificacionId = 2
        }

        btnComenta.setOnClickListener {
            if(titulo.text.contains(getString(R.string.comoTeSientes))){
                //Toast.makeText(context, getString(R.string.msjNoSentimiento), Toast.LENGTH_LONG).show()
                Snackbar.make(view, getString(R.string.msjNoSentimiento), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(txtComenta.length()<=comentarioMaximo){
                //Toast.makeText(context, getString(R.string.msjNoComentario), Toast.LENGTH_LONG).show()
                Snackbar.make(view, getString(R.string.msjNoComentario), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val comenta = titulo.text.toString()

                    //val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
                    //val defaultValue = ""
                    val token = tools.getDefaultsString(getString(R.string.token), requireContext()) //sharedPref.getString(getString(R.string.token), defaultValue)

            val apiService = RestApiService()
            val calificacion = Calificacion(
                    idCalificacion = null,
                    calificacion = calificacionId,
                    emocion = txtComenta.text.toString(),
                    fechaCreacion = null,
                    idUsuario = userName
            );
            apiService.calificacionFun(calificacion, token.toString()){
                if (it == 200) {
                    // it = newly added user parsed as response
                    // it?.id = newly added user ID
                    val action = TestFragmentDirections.actionTestFragmentToResponseFragment(comenta)
                    view.findNavController().navigate(action)
                } else {
                    //Timber.d("Error registering new user")
                    //Toast.makeText(context, "Ocurrio un error al guardar tu emoción", Toast.LENGTH_LONG).show()
                    Snackbar.make(view, resources.getText(R.string.msjNoSeEncuentraRegistrado), Snackbar.LENGTH_SHORT).show()
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
        enojado.setImageResource(R.drawable.ic_enojado_r)
        motivado.setImageResource(R.drawable.ic_motivado_r)
        estresado.setImageResource(R.drawable.ic_estresado_r)
        feliz.setImageResource(R.drawable.ic_feliz_r)
        neutral.setImageResource(R.drawable.ic_tranquilo_r)
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