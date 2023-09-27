package com.rep.studybuddy.model.apiService

import com.rep.studybuddy.model.modelData.LogIn
import com.rep.studybuddy.model.modelData.postLogIn
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    fun iniciarSesion(
        @Body logIn: LogIn
    ): Call<postLogIn>
}