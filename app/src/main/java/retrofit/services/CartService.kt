package retrofit.services

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.toastNotification
import com.example.myapplication.tokenService
import data.models.Cart
import retrofit.RetrofitClient
import retrofit.interfaces.ICartRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CartService {

    private val retrofitClient: RetrofitClient = RetrofitClient

    private val retrofit: Retrofit = retrofitClient.getRetrofit()

    private val iCartRepository: ICartRepository = retrofit.create(ICartRepository::class.java)

    fun getCartByOrder(id: Int, context: Context): LiveData<List<Cart>?> {
        val call = iCartRepository.getCartByOrder(tokenService.getRequestToken(), id)
        val result = MutableLiveData<List<Cart>?>()
        call.enqueue(object: Callback<List<Cart>> {

            override fun onResponse(call: Call<List<Cart>>, response: Response<List<Cart>>)
            {
                if(response.code() == 200) {
                    Log.d("CS", "Getting carts")
                    result.postValue(response.body())
                }
                else{
                    toastNotification.showLongMessage(context, "Данные введены неверно")
                    result.postValue(null)
                }
            }

            override fun onFailure(call: Call<List<Cart>>, t: Throwable)
            {
                Log.e("CS", "Server is not available")
                toastNotification.showLongMessage(context, "Сервер не отвечает, попробуйте позже")
                result.postValue(null)
            }
        })
        return result
    }
}