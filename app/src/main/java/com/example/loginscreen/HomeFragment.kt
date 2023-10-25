package com.example.loginscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Adapter
import android.widget.ProgressBar

import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginscreen.adaptors.CustomAdaptor
import com.example.loginscreen.models.CarList

import com.example.loginscreen.models.CarModel
import com.example.loginscreen.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.coroutineScope


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

    private val handler = Handler(Looper.getMainLooper())

    private var isScrolling : Boolean = false
    lateinit var carData : List<CarModel>
    lateinit var tempCarList : List<CarModel>
    var pageSize : Int =10;


    lateinit var adapter :CustomAdaptor
    lateinit var recyclerView:RecyclerView
    var progressBar:ProgressBar?=null
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


        progressBar =view.findViewById(R.id.progressBar) as ProgressBar

        val viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)



        viewModel.carLiveData.observe(viewLifecycleOwner){

            Log.d("GET CAR DATA", it.carList.first().carManufactureName)

            carData=it.carList
            tempCarList= it.carList.subList(0,minOf(pageSize, it.carList.size-1))

            adapter = CustomAdaptor(tempCarList, { onItemClickListener(it) })
            recyclerView.adapter = adapter
            progressBar!!.visibility=View.GONE

        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (isScrolling && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        isScrolling=false
                        loadMoreData()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){

                    isScrolling=true
                }
            }
        })


        viewModel.getCarData()



        return view
    }


    @SuppressLint("NotifyDataSetChanged")
    fun loadMoreData() {


        val nextPageStart = tempCarList.size
        val nextPageEnd = minOf(nextPageStart + pageSize,carData.size)

        progressBar!!.visibility=View.VISIBLE

        handler.postDelayed({
//            tempCarList.addAll(carData.subList(nextPageStart, nextPageEnd))
            tempCarList += carData.subList(nextPageStart, nextPageEnd)

            Log.d("LIST UPDATE",tempCarList.size.toString())
            adapter.setData(tempCarList)
            adapter.notifyDataSetChanged()
            progressBar!!.visibility=View.GONE

        }, 5000)


    }


    companion object {

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