package retrofit.services

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.toastNotification
import com.example.myapplication.tokenService
import data.models.Delivery
import data.modelsDTO.PostDelivery
import retrofit.RetrofitClient
import retrofit.interfaces.IDeliveryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DeliveryService {

    private val retrofitClient: RetrofitClient = RetrofitClient

    private val retrofit: Retrofit = retrofitClient.getRetrofit()

    private val iDeliveryRepository: IDeliveryRepository = retrofit.create(IDeliveryRepository::class.java)

    fun get(id: Int, context: Context): Delivery?{
        val call = iDeliveryRepository.get(tokenService.getRequestToken(), id)
        var result: Delivery? = null
        call.enqueue(object: Callback<Delivery> {

            override fun onResponse(call: Call<Delivery>, response: Response<Delivery>)
            {
                if(response.code() == 200){
                    result = response.body()
                }
                else{
                    toastNotification.showLongMessage(context, "Данные введены неверно")
                }
            }

            override fun onFailure(call: Call<Delivery>, t: Throwable)
            {
                Log.e("DS", "Server is not responding")
                toastNotification.showLongMessage(context, "Сервер не отвечает")
            }
        })
        return result
    }

    fun getActive(context: Context): LiveData<List<Delivery>?>{
        val call = iDeliveryRepository.getActive(tokenService.getRequestToken())
        val result = MutableLiveData<List<Delivery>?>()
        call.enqueue(object: Callback<List<Delivery>> {

            override fun onResponse(call: Call<List<Delivery>>, response: Response<List<Delivery>>)
            {
                if(response.code() == 200) {
                    Log.d("DS", "Getting active deliveries")
                    result.postValue(response.body())
                }
                else{
                    result.postValue(null)
                    Log.d("DS", "Invalid data on getting active deliveries")
                    toastNotification.showLongMessage(context, "Данные введены неверно")
                }
            }

            override fun onFailure(call: Call<List<Delivery>>, t: Throwable)
            {
                result.postValue(null)
                Log.d("DS", "Server is not responding")
                toastNotification.showLongMessage(context, "Сервер не отвечает")
            }
        })
        return result
    }

    fun getCourierActive(courierId: Int, context: Context): LiveData<List<Delivery>?>{
        val call = iDeliveryRepository.getCourierActive(tokenService.getRequestToken(), courierId)
        val result = MutableLiveData<List<Delivery>?>()
        call.enqueue(object: Callback<List<Delivery>> {

            override fun onResponse(call: Call<List<Delivery>>, response: Response<List<Delivery>>)
            {
                if(response.code() == 200) {
                    Log.d("DS", "Getting courier active deliverys")
                    result.postValue(response.body())
                }
                else{
                    result.postValue(null)
                    Log.d("DS", "Invalid data on getting courier active delivers")
                    toastNotification.showLongMessage(context, "Данные введены неверно")
                }
            }

            override fun onFailure(call: Call<List<Delivery>>, t: Throwable)
            {
                result.postValue(null)
                Log.d("DS", "Server is not responding")
                toastNotification.showLongMessage(context, "Сервер не отвечает")
            }
        })
        return result
    }

    fun assignCourier(body: PostDelivery, context: Context): LiveData<Delivery?>{
        val call = iDeliveryRepository.assignCourier(tokenService.getRequestToken(), body)
        val result = MutableLiveData<Delivery?>()
        call.enqueue(object: Callback<Delivery> {

            override fun onResponse(call: Call<Delivery>, response: Response<Delivery>)
            {
                if(response.code() == 200) {
                    result.postValue(response.body())
                }
                else{
                    result.postValue(null)
                    toastNotification.showLongMessage(context, "Данные введены неверно")
                }
            }

            override fun onFailure(call: Call<Delivery>, t: Throwable)
            {
                result.postValue(null)
                toastNotification.showLongMessage(context, "Сервер не отвечает")
            }
        })
        return result
    }
}