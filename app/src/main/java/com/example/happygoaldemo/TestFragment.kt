package com.example.happygoaldemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.happygoaldemo.databinding.FragmentTestBinding
import com.example.happygoaldemo.ui.test.TestViewModel
import com.example.happygoaldemo.ui.test.TestViewModelFactory

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
    private var comentarioMaximo: Long = 15;

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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
        testViewModel = ViewModelProvider(this, TestViewModelFactory()).get(TestViewModel::class.java)
        val lnlTest = binding.lnlTest
        val enojado = binding.imgEnojado
        val estresado = binding.imgEstresado
        val feliz = binding.imgFeliz
        val tranquilo = binding.imgTranquilo
        val motivado = binding.imgMotivado
        val titulo = binding.txtTitulo
        var btnComenta = binding.btnComenta
        var txtComenta = binding.txtComenta

        enojado.setOnClickListener{
            titulo.setText(R.string.enojado)
            titulo.setTextColor(resources.getColor(R.color.white))
            lnlTest.setBackgroundColor(resources.getColor(R.color.enojado))
            setVisible(view)
        }
        feliz.setOnClickListener {
            titulo.setText(R.string.feliz)
            titulo.setTextColor(resources.getColor(R.color.black))
            lnlTest.setBackgroundColor(resources.getColor(R.color.feliz))
            setVisible(view)
        }
        estresado.setOnClickListener {
            titulo.setText(R.string.estresado)
            titulo.setTextColor(resources.getColor(R.color.white))
            lnlTest.setBackgroundColor(resources.getColor(R.color.estresado))
            setVisible(view)
        }
        tranquilo.setOnClickListener {
            titulo.setText(R.string.tranquilo)
            titulo.setTextColor(resources.getColor(R.color.black))
            lnlTest.setBackgroundColor(resources.getColor(R.color.tranquilo))
            setVisible(view)
        }
        motivado.setOnClickListener {
            titulo.setText(R.string.motivado)
            titulo.setTextColor(resources.getColor(R.color.black))
            lnlTest.setBackgroundColor(resources.getColor(R.color.motivado))
            setVisible(view)
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
            val action = TestFragmentDirections.actionTestFragmentToResponseFragment(comenta)
            view.findNavController().navigate(action)
        }

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