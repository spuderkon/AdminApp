package retrofit.interfaces

import data.models.Role
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface IRoleRepository {

    companion object{
        const val path = "/Role";
    }

    @GET("$path/GetAll")
    fun getAll(@Header("Authorization") token: String) : Call<List<Role>>

}