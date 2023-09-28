package com.rep.studybuddy.ui.ui.perfil

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rep.studybuddy.aplication.Aplication.Companion.prefs
import com.rep.studybuddy.databinding.FragmentPerfilBinding
import com.rep.studybuddy.model.modelData.Message
import com.rep.studybuddy.ui.MainActivity.Companion.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Perfil : Fragment() {
    private lateinit var binding: FragmentPerfilBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        binding.btnCerrarSesion.setOnClickListener {
            closedSesion()
        }

        getDataUser()
        return binding.root
    }

    private fun closedSesion() {
        val token = prefs.getToken().toString()
        Log.e("TAG", "closedSesion: $token")
        service.logOut(token).enqueue(object:Callback<Message>{
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                Log.e("TAG", "onResponse: ${response}", )
                if (response.isSuccessful){
                    val data = response.body()
                    Log.e("TAG", "onResponse: $data", )
                    logOutSesion(data)
                }
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
            }

        })
    }

    private fun logOutSesion(data: Message?) {
        Log.e("TAG", "onResponse: $data")
        prefs.clear()
        activity?.finish()
    }

    private fun getDataUser() {
        val id = prefs.getIdUser().toString()
        Log.e("TAG", "getDataUser: $id")
        try {
            service.getUser(id).enqueue(object : Callback<Map<String, Any>> {
                override fun onResponse(
                    call: Call<Map<String, Any>>,
                    response: Response<Map<String, Any>>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        Log.e("TAG", "onResponse: $data")
                        //data convertir a json en posicion 1
                        val dataUser = data?.get("userFound") as Map<String, Any>
                        val names = "${dataUser["name"]}  ${dataUser["lastName"]}"
                        binding.tvNombre.text = names
                        binding.tvCorreo.text = dataUser["email"].toString()
                        binding.tvPrograma.text = dataUser["program"].toString()
                        binding.tvFicha.text = dataUser["course"].toString()


                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}")
                }

            })
        } catch (e: Exception) {
            Log.e("TAG", "getDataUser: ${e.message}")
        }
    }


}