package com.example.happygoaldemo

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.navArgs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResponseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResponseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val args: ResponseFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tv: TextView = view.findViewById(R.id.txtAlternText)
        var lnlResponse: LinearLayout = view.findViewById(R.id.lnlResponse);
        val calificacion = args.calificacion;
        var color = 111
        if(calificacion.contains(getString(R.string.feliz))){
            tv.setText(R.string.felizSentence)
            tv.setTextColor(resources.getColor(R.color.black))
            color = resources.getColor(R.color.feliz)
        }else if(calificacion.contains(getString(R.string.enojado))){
            tv.setText(R.string.enojadoSentence)
            tv.setTextColor(resources.getColor(R.color.white))
            color = resources.getColor(R.color.enojado)
        }else if(calificacion.contains(getString(R.string.estresado))){
            tv.setText(R.string.estresadoSentence)
            tv.setTextColor(resources.getColor(R.color.white))
            color = resources.getColor(R.color.estresado)
        }else if(calificacion.contains(getString(R.string.motivado))){
            tv.setText(R.string.motivadoSentence)
            tv.setTextColor(resources.getColor(R.color.black))
            color = resources.getColor(R.color.motivado)
        }else if(calificacion.contains(getString(R.string.tranquilo))){
            tv.setText(R.string.tranquiloSentence)
            tv.setTextColor(resources.getColor(R.color.black))
            color = resources.getColor(R.color.tranquilo)
        }
        lnlResponse.setBackgroundColor(color)
    }

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
        return inflater.inflate(R.layout.fragment_response, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment responseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResponseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}