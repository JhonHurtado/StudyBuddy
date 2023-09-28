package com.rep.studybuddy.model.apiService

import com.rep.studybuddy.model.modelData.LogIn
import com.rep.studybuddy.model.modelData.RegisterUser
import com.rep.studybuddy.model.modelData.postLogIn
import com.rep.studybuddy.model.modelData.postResult
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    fun iniciarSesion(
        @Body logIn: LogIn
    ): Call<postLogIn>

    @POST("api/user")
    fun registro(
        @Body registro: RegisterUser
    ): Call<postResult>
}