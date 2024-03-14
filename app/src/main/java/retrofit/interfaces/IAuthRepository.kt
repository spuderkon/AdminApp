package retrofit.interfaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface IAuthRepository {

    companion object{
        const val path = "/Auth";
    }

    @GET("$path/IsAuth")
    fun isAuth(@Header("Authorization") token: String) : Call<Void>

    @POST("$path/Authorize")
    fun authorize(@Query("phoneNumber") phoneNumber: String, @Query("password") password: String): Call<String>

    @POST("$path/SetNewPassword")
    fun setNewPassword(@Header("Authorization") token: String,
                      @Query("phoneNumber") phoneNumber: String,
                      @Query("password") password: String) : Call<Void>
}