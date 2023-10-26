package com.example.loginscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginscreen.adaptors.VehicleTypeAdapter
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
            carModel = it.getParcelable(ARG_CAR_MODEL) as CarModel?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        carModel?.let { Log.d("CAR DETAILS FRAGMENT", it.carManufactureName) }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val carManufactureName: TextView? = view?.findViewById(R.id.carManufactureName)
        val countryName: TextView? = view.findViewById(R.id.countryName)
        val carImage: ImageView? = view.findViewById(R.id.carImage)
        val vehicleType: RecyclerView = view.findViewById(R.id.vehicleTypesContainer)

        vehicleType.layoutManager = LinearLayoutManager(this.context)

        carModel?.let {
            carImage?.setImageResource(it.carImage)
            carManufactureName?.text = it.carManufactureName
            countryName?.text = it.countryName

            val adapter = VehicleTypeAdapter(carModel!!.vehicleType)

            vehicleType.adapter = adapter
        }


    }

    companion object {

        @JvmStatic
        fun newInstance(carModel: CarModel) =
            CarDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CAR_MODEL, carModel)
                }
            }
    }
}