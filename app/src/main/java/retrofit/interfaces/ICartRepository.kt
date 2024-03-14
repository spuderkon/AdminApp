package retrofit.interfaces

import data.models.Cart
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ICartRepository {

    companion object{
        const val path = "/Cart";
    }

    @GET("$path/GetCartByOrder/{id}")
    fun getCartByOrder(@Header("Authorization") token: String, @Path("id") id: Int) : Call<List<Cart>>
}