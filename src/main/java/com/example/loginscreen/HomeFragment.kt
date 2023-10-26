package com.example.loginscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginscreen.adaptors.CustomAdaptor
import com.example.loginscreen.models.CarModel
import com.example.loginscreen.viewmodel.HomeFragmentViewModel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {


    private var param1: String? = null
    private var param2: String? = null

    private var isScrolling: Boolean = false
    private var isInitialTime: Boolean = true
    private val recordsPerPage: Int = 20

    var tempCarList: List<CarModel> = listOf()
    lateinit var adapter: CustomAdaptor

    var progressBar: ProgressBar? = null

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

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(this.context)


        progressBar = view.findViewById(R.id.progressBar) as ProgressBar

        val viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)



        viewModel.carLiveData.observe(viewLifecycleOwner) {


            tempCarList += it.carList.subList(0, minOf(recordsPerPage, it.carList.size))


            if (isInitialTime) {

                adapter = CustomAdaptor(tempCarList, { onItemClickListener(it) })
                recyclerView.adapter = adapter
                isInitialTime = false
            } else {

                adapter.setData(tempCarList)
            }
            progressBar!!.visibility = View.GONE

        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (isScrolling && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    isScrolling = false


                    progressBar!!.visibility = View.VISIBLE
                    viewModel.getCarData(true)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {

                    isScrolling = true
                }
            }
        })


        viewModel.getCarData(true)


        return view
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

    //click listener for each recycler view item
    fun onItemClickListener(carModel: CarModel) {

        val fragment = CarDetailsFragment.newInstance(carModel)
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }


}