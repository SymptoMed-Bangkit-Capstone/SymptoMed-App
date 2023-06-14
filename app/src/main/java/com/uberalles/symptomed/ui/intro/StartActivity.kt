package com.uberalles.symptomed.ui.intro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        navHost()
    }

    private fun navHost() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.splash) as NavHostFragment
        navController = navHostFragment.navController
    }
}