package com.uberalles.symptomed.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.ActivityMainBinding
import com.uberalles.symptomed.ui.intro.StartActivity

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityMainBinding
    private lateinit var alertBuilder: AlertDialog.Builder
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE)
        firebaseAuth = FirebaseAuth.getInstance()

        setContentView(binding.root)

        navigationFragment(SymptomFragment())

        navigation()
        logoutButton()
    }

    private fun navigation() {
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> navigationFragment(SymptomFragment())
                R.id.nav_profile -> navigationFragment(ProfileFragment())
                R.id.nav_settings -> navigationFragment(SettingFragment())
            }
            true
        }
    }

    private fun navigationFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction =
            fragmentManager.beginTransaction().replace(R.id.fragment_container_main, fragment)
        fragmentTransaction.commit()
    }

    private fun logoutButton() {
        val editor = sharedPreferences.edit()
        val intent = Intent(this@MainActivity, StartActivity::class.java)

        binding.apply {
            btnLogout.setOnClickListener {
                alertBuilder = AlertDialog.Builder(this@MainActivity)
                alertBuilder.setTitle("Alert")
                    .setMessage("Do you want to log out?")
                    .setCancelable(true)
                    .setPositiveButton("Yes") { _, _ ->
                        FirebaseAuth.getInstance().signOut()
                        editor.clear()
                        editor.apply()
                        startActivity(intent)
                        finish()
                    }
                    .setNegativeButton("No") { dialogInterface, _ ->
                        dialogInterface.cancel()
                    }
                    .show()
            }
        }
    }

//    private fun toolbarText() {
//        val title = sharedPreferences.getString("name", "")
//        binding.apply {
//            toolbar.title = "Hello, $title!"
//        }
//    }

    override fun onBackPressed() {
        alertBuilder = AlertDialog.Builder(this@MainActivity)
        alertBuilder.setTitle("Alert")
            .setMessage("Do you want to exit the app?")
            .setCancelable(true)
            .setPositiveButton("Yes") { _, _ ->
                finish()
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .show()
    }

}