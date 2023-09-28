package com.rep.studybuddy.ui

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.rep.studybuddy.R
import com.rep.studybuddy.databinding.ActivityRegisterBinding
import com.rep.studybuddy.model.apiService.ApiService
import com.rep.studybuddy.model.modelData.RegisterUser
import com.rep.studybuddy.model.modelData.postResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.regex.Pattern

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var passwordConfirmed: String

    private lateinit var name: String
    private lateinit var lastName: String
    private lateinit var email: String
    private lateinit var dateBirth: String
    private lateinit var password: String
    private lateinit var numberId: Number
    private lateinit var typeId: String
    private lateinit var course: Number
    private lateinit var program: String

    private lateinit var service: ApiService



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edtDateBirth.setOnClickListener {
            showDatePickerDialog()
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://studybuddy-backend.vercel.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ApiService::class.java)


        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                validatePassword()
            }
        })

        binding.edtPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                validatePassword()
            }
        })

        binding.btnregistrarUsuarios.setOnClickListener{
            setData()
        }

    }

    private fun setData() {
        name = binding.edtNombre.text.toString()
        lastName = binding.edtApellidos.text.toString()
        email = binding.edtCorreo.text.toString()
        dateBirth = binding.edtDateBirth.text.toString()
        password = binding.edtPassword.text.toString()
        numberId = binding.edtNumberId.text.toString().toInt()
        typeId = binding.edtTypeDocument.text.toString()
        course = binding.edtCourse.text.toString().toInt()
        program = binding.program.text.toString()

        val data = RegisterUser(name, lastName, email, dateBirth, password, numberId, typeId, course, program)
        RegistrarUser(data)

    }

    private fun RegistrarUser(data: RegisterUser) {
        try {
            service.registro(data).enqueue(object : retrofit2.Callback<postResult>{
                override fun onResponse(
                    call: retrofit2.Call<postResult>,
                    response: retrofit2.Response<postResult>
                ) {
                    if (response.isSuccessful){
                        Log.d("TAG", "onResponse: ${response.body()}")
                    }else{
                        Log.d("TAG", "onResponse: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: retrofit2.Call<postResult>, t: Throwable) {
                    Log.e("TAG", "onFailure: $t", )
                }

            })
        }catch (e: Exception){
            Log.e("TAG", "RegistrarUser: $e", )
        }
    }


    private fun validateEmail() {
        val email = binding.edtCorreo
        val tlEmail = binding.tlyCorreo
        val emailText = email.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (emailText.isEmpty()) {
            tlEmail.error = "Campo requerido"
            tlEmail.isErrorEnabled = true
            binding.btnregistrarUsuarios.isEnabled = false
        } else if (!emailText.matches(emailPattern.toRegex())) {
            tlEmail.error = "Correo no válido"
            tlEmail.isErrorEnabled = true
            binding.btnregistrarUsuarios.isEnabled = false
        } else {
            tlEmail.isErrorEnabled = false
            binding.btnregistrarUsuarios.isEnabled = true
        }
    }
    private fun validatePassword() {
        val passwordDriver = binding.edtPassword
        val confirmPasswordDriver = binding.edtPasswordConfirm
        val password = passwordDriver.text.toString()
        val confirmPassword = confirmPasswordDriver.text.toString()

        val passwordPattern =
            Pattern.compile("^(?=.*[A-Z])(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")

        if (passwordPattern.matcher(password).matches()) {
            if (password == confirmPassword) {
                binding.tlyPasswordConfirm.error = null
                binding.tlyPasswordConfirm.helperText = "Las contraseñas coinciden"
                binding.tlyPasswordConfirm.setHelperTextColor(ColorStateList.valueOf(Color.BLACK))
                passwordConfirmed = confirmPassword
            } else {
                binding.tlyPasswordConfirm.error = "Las contraseñas no coinciden"
                binding.tlyPasswordConfirm.helperText = null
            }
        } else {
            passwordDriver.error =
                "La contraseña debe tener al menos 8 caracteres, 1 mayúscula y 1 carácter especial"
        }
    }



    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // Aquí puedes manejar la fecha seleccionada
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                // Formatear la fecha como desees, por ejemplo:
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)

                // Establecer la fecha formateada en el EditText
                binding.edtDateBirth.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Mostrar el DatePickerDialog
        datePickerDialog.show()
    }
}