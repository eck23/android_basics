package com.example.loginscreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.loginscreen.models.CarModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CarDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var carModel: CarModel? = null
    private val ARG_CAR_MODEL = "carModel"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            carModel = it.getSerializable(ARG_CAR_MODEL) as CarModel?
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {




        carModel?.let { Log.d("CAR DETAILS FRAGMENT", it.carManufactureName) }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_details, container, false)
    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         val carNameText : TextView? = view?.findViewById(R.id.car_name_text)

         carNameText?.text = carModel?.carManufactureName

    }

    companion object {

        @JvmStatic
        fun newInstance(carModel: CarModel) =
            CarDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CAR_MODEL, carModel)
                }
            }
    }
}