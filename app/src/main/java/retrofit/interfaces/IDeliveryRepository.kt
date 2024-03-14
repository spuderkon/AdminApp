package retrofit.interfaces

import data.models.Delivery
import data.modelsDTO.PostDelivery
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface IDeliveryRepository {

    companion object{
        const val path = "/Delivery";
    }

    @GET("$path/Get/{id}")
    fun get(@Header("Authorization") token: String, @Path("id") id: Int) : Call<Delivery>

    @GET("$path/GetActive")
    fun getActive(@Header("Authorization") token: String): Call<List<Delivery>>

    @GET("$path/GetCourierActive/{id}")
    fun getCourierActive(@Header("Authorization") token: String, @Path("id") id: Int): Call<List<Delivery>>

    @POST("$path/AssignCourier")
    fun assignCourier(@Header("Authorization") token: String, @Body body: PostDelivery): Call<Delivery>
}