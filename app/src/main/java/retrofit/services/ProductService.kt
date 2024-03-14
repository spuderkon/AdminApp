package retrofit.services

import android.util.Log
import retrofit.RetrofitClient
import data.models.Product
import data.modelsDTO.PostProduct
import retrofit.interfaces.IProductRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProductService {

    private val retrofitClient: RetrofitClient = RetrofitClient

    private val retrofit: Retrofit = retrofitClient.getRetrofit()

    private val iProductRepository: IProductRepository = retrofit.create(IProductRepository::class.java)

    fun getAll(token: String): List<Product>?{
        val call = iProductRepository.getAll(token)
        var result: List<Product>? = null
        call.enqueue(object: Callback<List<Product>> {

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>)
            {
                result = response.body()
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable)
            {
                Log.e("PS", "Get all products error")
            }
        })
        return result
    }

    fun getByParent(token: String, id: Int): List<Product>?{
        val call = iProductRepository.getByParent(token, id)
        var result: List<Product>? = null
        call.enqueue(object: Callback<List<Product>> {

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>)
            {
                result = response.body()
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable)
            {
                Log.e("PS", "Get products by paren error")
            }
        })
        return result
    }

    fun add(token: String, body: PostProduct): Product?{
        val call = iProductRepository.add(token, body)
        var result: Product? = null
        call.enqueue(object: Callback<Product> {

            override fun onResponse(call: Call<Product>, response: Response<Product>)
            {
                result = response.body()
            }

            override fun onFailure(call: Call<Product>, t: Throwable)
            {
                Log.e("PS", "Add product error")
            }
        })
        return result
    }
}