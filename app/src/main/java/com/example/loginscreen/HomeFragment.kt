package com.example.loginscreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginscreen.adaptors.CustomAdaptor

import com.example.loginscreen.models.CarModel
import com.example.loginscreen.viewmodel.HomeFragmentViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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


        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView= view.findViewById<RecyclerView>(R.id.recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(this.context)


        val progressBar =view.findViewById(R.id.progressBar) as ProgressBar

        val viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)


        viewModel.carLiveData.observe(viewLifecycleOwner){

            Log.d("GET CAR DATA", it.carList.first().carManufactureName)

            val adapter = CustomAdaptor(it.carList, { onItemClickListener(it) })
            recyclerView.adapter = adapter
            progressBar.visibility=View.GONE

        }

        viewModel.getCarData()



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }



    }

    fun onItemClickListener(carModel: CarModel) {

        val fragment = CarDetailsFragment.newInstance(carModel)
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.container,fragment)
            ?.addToBackStack(null)
            ?.commit()
    }


}