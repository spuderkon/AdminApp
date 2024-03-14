package retrofit.interfaces


import data.models.Product
import data.modelsDTO.PostProduct
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface IProductRepository {

    companion object{
        const val path = "/Product";
    }

    @GET("$path/GetAll")
    fun getAll(@Header("Authorization") token: String) : Call<List<Product>>

    @GET("$path/GetByParent/{id}")
    fun getByParent(@Header("Authorization") token: String, @Path("id") id: Int): Call<List<Product>>

    @POST("$path/Add")
    fun add(@Header("Authorization") token: String, @Body body: PostProduct): Call<Product>
}