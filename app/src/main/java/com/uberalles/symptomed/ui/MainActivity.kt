package com.uberalles.symptomed.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.ActivityMainBinding
import com.uberalles.symptomed.intro.StartActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var alertBuilder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragment = DetectionFragment()
        fragmentManager.beginTransaction().replace(R.id.fragment_container_main, fragment)
            .commit()

        logoutButton()
        toolbarText()
    }

    private fun logoutButton() {
        binding.apply {
            btnLogout.setOnClickListener {

                alertBuilder = AlertDialog.Builder(this@MainActivity)
                alertBuilder.setTitle("Alert")
                    .setMessage("Do you want to log out?")
                    .setCancelable(true)
                    .setPositiveButton("Yes") { _, _ ->
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(this@MainActivity, StartActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .setNegativeButton("No") { dialogInterface, it ->
                        dialogInterface.cancel()
                    }
                    .show()
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