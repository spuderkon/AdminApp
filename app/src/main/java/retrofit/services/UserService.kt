package retrofit.services

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.toastNotification
import com.example.myapplication.tokenService
import data.models.Cart
import data.models.User
import data.modelsDTO.PostUser
import retrofit.RetrofitClient
import retrofit.interfaces.IUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UserService {

    private val retrofitClient: RetrofitClient = RetrofitClient

    private val retrofit: Retrofit = retrofitClient.getRetrofit()

    private val iUserRepository: IUserRepository = retrofit.create(IUserRepository::class.java)

    fun get(id: Int, context: Context) : LiveData<User?>{
        val call = iUserRepository.get(tokenService.getRequestToken(), id)
        val result = MutableLiveData<User?>()
        call.enqueue(object: Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>)
            {
                if(response.code() == 200) {
                    Log.d("UsS", "Getting user by id")
                    result.postValue(response.body())
                }
                else{
                    Log.e("UsS", "Invalid data getting user by id")
                    toastNotification.showLongMessage(context, "Данные введены неверно")
                    result.postValue(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable)
            {
                Log.e("UsS", "Get user by id error")
            }
        })
        return result
    }

    fun getMy(context: Context) : LiveData<User?>{
        val call = iUserRepository.getMy(tokenService.getRequestToken())
        var result = MutableLiveData<User?>()
        call.enqueue(object: Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>)
            {
                if(response.code() == 200) {
                    Log.d("UsS", "Getting me")
                    result.postValue(response.body())
                }
                else{
                    Log.e("UsS", "Invalid data getting me")
                    toastNotification.showLongMessage(context, "Данные введены неверно")
                    result.postValue(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable)
            {
                Log.e("UsS", "Server is not responding")
                toastNotification.showLongMessage(context, "Сервер не отвечает, попробуйте позже")
                result.postValue(null)
            }

        })
        return result
    }

    fun getCouriers(context: Context) : LiveData<List<User>?> {
        val call = iUserRepository.getCouriers(tokenService.getRequestToken())
        val result = MutableLiveData<List<User>?>()
        call.enqueue(object: Callback<List<User>> {

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>)
            {
                if(response.code() == 200) {
                    Log.d("UsS", "Getting couriers")
                    result.postValue(response.body())
                }
                else{
                    Log.e("UsS", "Invalid data getting couriers")
                    toastNotification.showLongMessage(context, "Данные введены неверно")
                    result.postValue(null)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable)
            {
                Log.e("UsS", "Server is not responding")
                toastNotification.showLongMessage(context, "Сервер не отвечает, попробуйте позже")
                result.postValue(null)
            }

        })
        return result
    }

    fun getEmployees(context: Context) : LiveData<List<User>?> {
        val call = iUserRepository.getEmployees(tokenService.getRequestToken())
        val result = MutableLiveData<List<User>?>()
        call.enqueue(object: Callback<List<User>> {

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>)
            {
                if(response.code() == 200) {
                    Log.d("UsS", "Getting users")
                    result.postValue(response.body())
                }
                else{
                    Log.e("UsS", "Invalid data getting users")
                    toastNotification.showLongMessage(context, "Данные введены неверно")
                    result.postValue(null)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable)
            {
                Log.e("UsS", "Server is not responding")
                toastNotification.showLongMessage(context, "Сервер не отвечает, попробуйте позже")
                result.postValue(null)
            }
        })
        return result
    }

    fun add(body: PostUser, context: Context) : LiveData<User?>{
        val call = iUserRepository.add(tokenService.getRequestToken(), body)
        val result = MutableLiveData<User?>()
        call.enqueue(object: Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>)
            {
                if(response.code() == 200) {
                    Log.d("UsS", "Creating user")
                    result.postValue(response.body())
                }
                else{
                    Log.e("UsS", "Invalid data creating users")
                    toastNotification.showLongMessage(context, "Данные введены неверно")
                    result.postValue(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable)
            {
                Log.e("UsS", "Server is not responding")
                toastNotification.showLongMessage(context, "Сервер не отвечает, попробуйте позже")
                result.postValue(null)
            }
        })
        return result
    }
}