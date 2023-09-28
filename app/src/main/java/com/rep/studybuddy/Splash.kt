package com.rep.studybuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Looper
import android.util.Log
import com.rep.studybuddy.R
import com.rep.studybuddy.aplication.Aplication.Companion.prefs
import com.rep.studybuddy.ui.MainActivity
import com.rep.studybuddy.ui.StudyBuddyHome

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        object : CountDownTimer(5000, 1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                val recuerdame = prefs.getRecuerdame()
                Log.e("TAG", "onFinish: $recuerdame", )
                if (recuerdame){
                    val intent = Intent(this@Splash, StudyBuddyHome::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    startActivity(Intent(this@Splash, MainActivity::class.java))
                    finish()
                }

            }

        }.start()
    }
}