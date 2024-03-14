package retrofit.services

import android.util.Log
import data.models.Unit
import data.modelsDTO.PostUnit
import retrofit.RetrofitClient
import retrofit.interfaces.IUnitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UnitService {

    private val retrofitClient: RetrofitClient = RetrofitClient

    private val retrofit: Retrofit = retrofitClient.getRetrofit()

    private val iUnitRepository: IUnitRepository = retrofit.create(IUnitRepository::class.java)

    fun getAll(token: String): List<Unit>?{
        val call = iUnitRepository.getAll(token)
        var result: List<Unit>? = null
        call.enqueue(object: Callback<List<Unit>> {

            override fun onResponse(call: Call<List<Unit>>, response: Response<List<Unit>>)
            {
                result = response.body()
            }

            override fun onFailure(call: Call<List<Unit>>, t: Throwable)
            {
                Log.e("UnS", "Get all units error")
            }
        })
        return result
    }

    fun add(token: String, body: PostUnit): Unit?{
        val call = iUnitRepository.add(token, body)
        var result: Unit? = null
        call.enqueue(object: Callback<Unit> {

            override fun onResponse(call: Call<Unit>, response: Response<Unit>)
            {
                result = response.body()
            }

            override fun onFailure(call: Call<Unit>, t: Throwable)
            {
                Log.e("UnS", "Get all units error")
            }
        })
        return result
    }
}