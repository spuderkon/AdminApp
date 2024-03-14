package retrofit.interfaces

import data.models.Unit
import data.modelsDTO.PostUnit
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface IUnitRepository {

    companion object{
        const val path = "/Unit";
    }

    @GET("$path/GetAll")
    fun getAll(@Header("Authorization") token: String): Call<List<Unit>>

    @POST("$path/Add")
    fun add(@Header("Authorization") token: String, @Body body: PostUnit): Call<Unit>
}