package retrofit.services

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.toastNotification
import com.example.myapplication.tokenService
import retrofit.RetrofitClient
import retrofit.interfaces.IAuthRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AuthService() {

    private val retrofitClient: RetrofitClient = RetrofitClient

    private val retrofit: Retrofit = retrofitClient.getRetrofit()

    private val iAuthRepository: IAuthRepository = retrofit.create(IAuthRepository::class.java)

    fun authorize(phoneNumber: String, password: String, context: Context): LiveData<String?>{
        val call = iAuthRepository.authorize(phoneNumber, password)
        val result = MutableLiveData<String?>()
        call.enqueue(object: Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>)
            {
                if(response.code() == 200) {
                    Log.d("AS", "Authorizing")
                    result.postValue(response.body().toString())
                }
                else{
                    toastNotification.showLongMessage(context, "Данные введены неверно")
                    result.postValue(null)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable)
            {
                Log.e("AS", "Server is not available")
                toastNotification.showLongMessage(context, "Сервер не отвечает, попробуйте позже")
                result.postValue(null)
            }
        })
        return result
    }

    fun isAuth(context: Context) {
        val call = iAuthRepository.isAuth(tokenService.getRequestToken())
        call.enqueue(object: Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>)
            {
                if(response.code() == 200){
                    Log.d("AS", "Is authorized")
                }
                else{
                    toastNotification.showLongMessage(context, "Данные введены неверно")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable)
            {
                Log.e("AS", "IsAuth error")
                toastNotification.showLongMessage(context, "Сервер не отвечает, попробуйте позже")
            }
        })
    }

    fun setNewPassword(phoneNumber: String, password: String, context: Context){
        val call = iAuthRepository.setNewPassword(tokenService.getRequestToken() ,phoneNumber, password)

        call.enqueue(object: Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>)
            {
                if(response.code() == 200){
                    Log.d("AS", "Setting new password")
                }
                else{
                    toastNotification.showLongMessage(context, "Данные введены неверно")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable)
            {
                Log.e("AS", "SetNewPassword error")
                toastNotification.showLongMessage(context, "Сервер не отвечает, попробуйте позже")
            }
        })
    }
}