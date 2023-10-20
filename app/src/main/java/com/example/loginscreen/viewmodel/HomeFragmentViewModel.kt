package com.example.loginscreen.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginscreen.R
import com.example.loginscreen.api.APIService
import com.example.loginscreen.api.RetrofitInstance
import com.example.loginscreen.api.values.CAR_BASE_URL
import com.example.loginscreen.models.CarList
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeFragmentViewModel : ViewModel() {

     var carLiveData : MutableLiveData<CarList> = MutableLiveData()

     private val carImages: IntArray = intArrayOf(R.drawable.car_blue, R.drawable.car_red, R.drawable.car_green)

     fun getCarData(){

          viewModelScope.launch {

               val retrofit = RetrofitInstance.getInstance(CAR_BASE_URL)

               val service = retrofit.create(APIService::class.java)

               try {
                    val carData = service.getCarNames()

                    for (car in carData.carList){

                         car.carImage= carImages[Random.nextInt(3)]
                    }

                    carLiveData.value=carData

               }catch (e:Exception){

                         Log.d("GET CAR DATA","ERROR IN GETTING DATA")
               }
          }

     }
}