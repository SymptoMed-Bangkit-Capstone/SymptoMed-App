package com.uberalles.symptomed.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uberalles.symptomed.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbarText()
    }

    private fun toolbarText() {
        binding.apply {
            toolbar.title = "Hello, ${intent.getStringExtra(EXTRA_NAME)}!"
        }
    }

    companion object {
        const val TAG = "MainActivity"

        private const val EXTRA_NAME = "extra_name"
        private const val EXTRA_GENDER = "extra_gender"
        private const val EXTRA_AGE = "extra_age"

    }
}