package com.uberalles.symptomed.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.ActivityMainBinding
import com.uberalles.symptomed.ui.intro.NameFragment
import com.uberalles.symptomed.ui.intro.StartActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var alertBuilder: AlertDialog.Builder
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        setContentView(binding.root)

        navigationFragment(HomeFragment())
        navigation()
    }

    private fun navigation() {
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> navigationFragment(HomeFragment())
                R.id.nav_faq -> navigationFragment(FaqFragment())
                R.id.nav_profile -> navigationFragment(ProfileFragment())
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

    fun logout(){
        val intent = Intent(this@MainActivity, StartActivity::class.java)
        alertBuilder = AlertDialog.Builder(this@MainActivity)
        alertBuilder.setTitle("Alert")
            .setMessage("Do you want to log out?")
            .setCancelable(true)
            .setPositiveButton("Yes") { _, _ ->
                FirebaseAuth.getInstance().signOut()
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .show()
    }

    fun backToProfile(){
        navigationFragment(ProfileFragment())
    }

    fun backToHome(){
        navigationFragment(HomeFragment())
    }

    fun onlineFragment(){
        navigationFragment(OnlineSymptomFragment())
    }

    fun hideNavBottom(boolean: Boolean) {
        val navBottom = binding.bottomNavView
        if (boolean) {
            navBottom.visibility = View.GONE
        } else {
            navBottom.visibility = View.VISIBLE
        }
    }

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