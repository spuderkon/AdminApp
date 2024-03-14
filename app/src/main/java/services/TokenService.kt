package services

import android.content.SharedPreferences
import android.util.Log
import com.auth0.android.jwt.JWT


class TokenService() {

    private lateinit var _token: String

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var spEditor: SharedPreferences.Editor

    /*
    * private lateinit var sharedPreferences: SharedPreferences
    *sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
    * */
    companion object Factory{
        fun create(initialToken: String, sp: SharedPreferences): TokenService {
            val x =  TokenService()
            x.setSharedPreferences(sp)
            x.setToken(initialToken)
            return x
        }
        fun isAuth(sp: SharedPreferences): Boolean{
            val t = sp.getString("token", "empty")
            return t != "empty" && !JWT(t!!).isExpired(0)
        }
        fun isAdmin(t: String): Boolean{
            return JWT(t).getClaim("role").asString() == "admin"
        }
    }

    fun setToken(token: String){
        Log.d("TS","Setting up token")
        _token = token
        spEditor.putString("token", _token)
        spEditor.apply()
    }

    fun setSharedPreferences(sp: SharedPreferences){
        Log.d("TS","Setting up shared preferences")
        sharedPreferences = sp
        spEditor = sp.edit()
    }

    fun getToken(): String{
        return _token
    }

    fun getRequestToken(): String{
        return "Bearer $_token"
    }

    fun tokenExpired(): Boolean{
        //var oldToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEiLCJyb2xlIjoiY291cmllciIsInBob25lTnVtYmVyIjoiNzk4MjY1NDg0MjkiLCJuYmYiOjE3MDc0ODM5OTUsImV4cCI6MTcwNzUwNTE0MCwiaWF0IjoxNzA3NDgzOTk1LCJpc3MiOiJNZWF0U3RvcmUiLCJhdWQiOiJNZWF0U3RvcmVBcHAifQ.EAgvQ5C6b9jw-hK1W4iCOORHjF5-WPK6BXuk-KV9aDU"
        return JWT(_token).isExpired(0)
    }

    fun isAuthorized(): Boolean{
        return _token != "empty" && !tokenExpired()
    }

    fun getRole(token: String?): String{
        return if (token == null) JWT(_token).getClaim("role").asString()!! else JWT(token).getClaim("role").asString()!!
    }
}