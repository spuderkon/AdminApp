package retrofit.services

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.toastNotification
import com.example.myapplication.tokenService
import data.models.Role
import retrofit.RetrofitClient
import retrofit.interfaces.IProductRepository
import retrofit.interfaces.IRoleRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RoleService {

    private val retrofitClient: RetrofitClient = RetrofitClient

    private val retrofit: Retrofit = retrofitClient.getRetrofit()

    private val iRoleRepository: IRoleRepository = retrofit.create(IRoleRepository::class.java)

    fun getAll(context: Context): LiveData<List<Role>?> {
        val call = iRoleRepository.getAll(tokenService.getRequestToken())
        val result = MutableLiveData<List<Role>?>()
        call.enqueue(object: Callback<List<Role>> {

            override fun onResponse(call: Call<List<Role>>, response: Response<List<Role>>)
            {
                if(response.code() == 200) {
                    Log.d("RS", "Getting roles")
                    result.postValue(response.body())
                }
                else {
                    Log.e("RS", "Invalid data getting roles")
                    toastNotification.showLongMessage(context, "Данные введены неверно")

                    result.postValue(null)
                }
            }

            override fun onFailure(call: Call<List<Role>>, t: Throwable)
            {
                Log.e("RS", "Getting roles error")
                toastNotification.showLongMessage(context, "Сервер не отвечает, попробуйте позже")
                result.postValue(null)
            }
        })
        return result
    }

}