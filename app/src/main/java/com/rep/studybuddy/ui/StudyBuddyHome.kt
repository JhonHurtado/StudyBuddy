package com.rep.studybuddy.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rep.studybuddy.R
import com.rep.studybuddy.databinding.ActivityStudyBuddyHomeBinding

class StudyBuddyHome : AppCompatActivity() {

    private lateinit var binding: ActivityStudyBuddyHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudyBuddyHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_study_buddy_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        setSupportActionBar(findViewById(R.id.toolbar))
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home, R.id.learning, R.id.perfil
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}