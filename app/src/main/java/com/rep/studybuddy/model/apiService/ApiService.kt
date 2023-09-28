package com.rep.studybuddy.model.apiService

import com.rep.studybuddy.model.modelData.LogIn
import com.rep.studybuddy.model.modelData.Message
import com.rep.studybuddy.model.modelData.RegisterUser
import com.rep.studybuddy.model.modelData.postLogIn
import com.rep.studybuddy.model.modelData.postResult
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/login")
    fun iniciarSesion(
        @Body logIn: LogIn
    ): Call<postLogIn>

    @POST("api/logOut")
    fun logOut(
        @Header("access-token") token: String
    ): Call<Message>

    @POST("api/user")
    fun registro(
        @Body registro: RegisterUser
    ): Call<postResult>

    @GET("api/user/{id}")
    fun getUser(
        @Path("id") id: String
    ): Call<Map<String, Any>>
}