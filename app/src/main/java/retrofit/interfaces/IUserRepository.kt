package retrofit.interfaces

import data.models.User
import data.modelsDTO.PostUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface IUserRepository {

    companion object{
        const val path = "/User"
    }

    @GET("$path/Get/{id}")
    fun get(@Header("Authorization") token: String, @Path("id") id: Int): Call<User>

    @GET("$path/GetMy")
    fun getMy(@Header("Authorization") token: String): Call<User>

    @GET("$path/GetCouriers")
    fun getCouriers(@Header("Authorization") token: String): Call<List<User>>

    @GET("$path/GetEmployees")
    fun getEmployees(@Header("Authorization") token: String): Call<List<User>>

    @POST("$path/Add")
    fun add(@Header("Authorization") token: String, @Body body: PostUser): Call<User>
}