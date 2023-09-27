package com.rep.studybuddy.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rep.studybuddy.aplication.Aplication.Companion.prefs
import com.rep.studybuddy.databinding.ActivityMainBinding
import com.rep.studybuddy.model.apiService.ApiService
import com.rep.studybuddy.model.modelData.LogIn
import com.rep.studybuddy.model.modelData.postLogIn
import com.rep.studybuddy.model.preferencias.Prefs
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var service: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://studybuddy-backend.vercel.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ApiService::class.java)


        binding.txtIrRegistro.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }


        binding.btnIniciarSesion.setOnClickListener {
            logIn()
        }

        binding.edtCorreo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }

    private fun validateEmail(email: String) {
        // Utilizar una expresión regular para validar la dirección de correo electrónico
        val emailPattern = Pattern.compile(
            "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.+[a-zA-Z]{2,4}"
        )

        val matcher = emailPattern.matcher(email)

        if (matcher.matches()) {
            binding.tylCorreo.error = null
        } else {
            binding.tylCorreo.error = "Correo electrónico no válido"
        }
    }


    private fun logIn() {
        val email = binding.edtCorreo
        val password = binding.edtPassword
        val recuerdame = binding.checkRecuerdame

        if (email.text.toString().isNotEmpty() || password.text.toString().isNotEmpty()) {
            val logIn = LogIn(email.text.toString(), password.text.toString())
            if (recuerdame.isChecked){
                prefs.saveRecuerdame(true)
            }
            logInApi(logIn)
        }else{
            Toast.makeText(this, "Campos vacios", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logInApi(logIn: LogIn) {
        try {
            service.iniciarSesion(logIn).enqueue(object : Callback<postLogIn> {
                override fun onResponse(call: Call<postLogIn>, response: Response<postLogIn>) {
                    if (response.isSuccessful) {
                        val postLogIn = response.body()
                        if (postLogIn != null) {
                            if (postLogIn.token.isNotEmpty() ){
                                prefs.saveToken(postLogIn.token)
                                startActivity(Intent(this@MainActivity, StudyBuddyHome::class.java))
                            }
                        }
                    } else {
                        response.errorBody()?.let {
                            Log.e("TAG", "logInApi: ${it.string()}")
                            Toast.makeText(this@MainActivity, it.string().toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<postLogIn>, t: Throwable) {
                    Log.e("TAG", "logInApi: ${t.message}")
                }

            })
        } catch (e: Exception) {
            Log.e("TAG", "logInApi: ${e.message}")
        }
    }
}