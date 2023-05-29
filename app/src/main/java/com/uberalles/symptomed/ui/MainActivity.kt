package com.uberalles.symptomed.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.uberalles.symptomed.R
import com.uberalles.symptomed.auth.AuthActivity
import com.uberalles.symptomed.auth.SignInFragment
import com.uberalles.symptomed.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logoutButton()
        toolbarText()
    }

    private fun logoutButton() {
        binding.apply {
            btnLogout.setOnClickListener {
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this@MainActivity, AuthActivity::class.java)
                startActivity(intent)
            }
        }
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