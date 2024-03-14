package retrofit.interfaces

import data.models.Order
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface IOrderRepository {

    companion object{
        const val path = "/Order";
    }

    @GET("$path/GetNotInDelivery")
    fun getNotInDelivery(@Header("Authorization") token: String) : Call<List<Order>>

    @PUT("$path/RejectOrder/{id}")
    fun rejectOrder(@Header("Authorization") token: String, @Path("id") id: Int): Call<Void>

}