package retrofit.services

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.toastNotification
import com.example.myapplication.tokenService
import data.models.Order
import retrofit.RetrofitClient
import retrofit.interfaces.IOrderRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class OrderService {

    private val retrofitClient: RetrofitClient = RetrofitClient

    private val retrofit: Retrofit = retrofitClient.getRetrofit()

    private val iOrderRepository: IOrderRepository = retrofit.create(IOrderRepository::class.java)

    fun getNotInDelivery(context: Context): LiveData<List<Order>?>{
        val call = iOrderRepository.getNotInDelivery(tokenService.getRequestToken())
        val result = MutableLiveData<List<Order>?>()
        call.enqueue(object: Callback<List<Order>> {

            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>)
            {
                if(response.code() == 200) {
                    Log.d("OS", "Getting orders")
                    result.postValue(response.body())
                }
                else {
                    result.postValue(null)
                    Log.e("OS", "Invalid data getting orders not in delivery")
                    toastNotification.showLongMessage(context, "Данные введены неверно")
                }
            }
            override fun onFailure(call: Call<List<Order>>, t: Throwable)
            {
                result.postValue(null)
                Log.e("OS", "Get order not in delivery error")
                toastNotification.showLongMessage(context, "Сервер не отвечает, попробуйте позже")
            }
        })
        return result
    }

    fun rejectOrder(id: Int,context: Context): LiveData<Boolean>{
        val call = iOrderRepository.rejectOrder(tokenService.getRequestToken(), id)
        val result = MutableLiveData<Boolean>()
        call.enqueue(object: Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>)
            {
                if(response.code() == 200) {
                    Log.d("OS", "Rejecting order")
                    result.postValue(true)
                    toastNotification.showLongMessage(context, "Заказ отменен")
                }
                else {
                    Log.e("OS", "Invalid data rejecting order")
                    toastNotification.showLongMessage(context, "Данные введены неверно")

                    result.postValue(false)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable)
            {
                Log.e("OS", "Reject order error")
                toastNotification.showLongMessage(context, "Сервер не отвечает, попробуйте позже")
                result.postValue(false)
            }
        })
        return result
    }
}