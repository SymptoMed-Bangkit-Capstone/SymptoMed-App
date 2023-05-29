package com.uberalles.symptomed.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHost()
    }

    private fun navHost() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.splash) as NavHostFragment
        navController = navHostFragment.navController
    }
}