package com.uberalles.symptomed.ui.main_activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.ActivityMainBinding
import com.uberalles.symptomed.ui.main_activity.navigation.AboutUsFragment
import com.uberalles.symptomed.ui.main_activity.navigation.FaqFragment
import com.uberalles.symptomed.ui.main_activity.navigation.HomeFragment
import com.uberalles.symptomed.ui.main_activity.navigation.ProfileFragment
import com.uberalles.symptomed.ui.main_activity.prediction.OfflineSymptomFragment
import com.uberalles.symptomed.ui.main_activity.prediction.OnlineSymptomFragment
import com.uberalles.symptomed.ui.start_activity.StartActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var alertBuilder: AlertDialog.Builder
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDb: FirebaseDatabase
    private lateinit var firebaseReference: DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDb =
            Firebase.database("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app")
        firebaseReference = firebaseDb.getReference("${firebaseAuth.currentUser?.uid}")



        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.bottomNavView.selectedItemId == R.id.nav_home) {
                    alertBuilder = AlertDialog.Builder(this@MainActivity)
                    alertBuilder.setTitle("Alert")
                        .setMessage("Apakah Anda ingin keluar dari aplikasi?")
                        .setCancelable(true)
                        .setPositiveButton("Ya") { _, _ ->
                            finish()
                            android.os.Process.killProcess(android.os.Process.myPid())
                        }
                        .setNegativeButton("Tidak") { dialogInterface, _ ->
                            dialogInterface.cancel()
                        }
                        .show()
                } else {
                    binding.bottomNavView.selectedItemId = R.id.nav_home
                }
            }
        })

        navigationFragment(HomeFragment())
        navigation()
    }


    private fun navigation() {
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> navigationFragment(HomeFragment())
                R.id.nav_faq -> navigationFragment(FaqFragment())
                R.id.nav_about_us -> navigationFragment(AboutUsFragment())
                R.id.nav_profile -> navigationFragment(ProfileFragment())
            }
            true
        }
    }

    private fun navigationFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragment_container_main, fragment)
            .disallowAddToBackStack().commit()
    }

    fun logout() {
        val intent = Intent(this@MainActivity, StartActivity::class.java)
        alertBuilder = AlertDialog.Builder(this@MainActivity)
        alertBuilder.setTitle("Alert")
            .setMessage("Apakah Anda ingin keluar dari aplikasi?")
            .setCancelable(true)
            .setPositiveButton("Ya") { _, _ ->
                FirebaseAuth.getInstance().signOut()
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Tidak") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .show()
    }

    fun backToProfile() {
        navigationFragment(ProfileFragment())
    }

    fun backToHome() {
        navigationFragment(HomeFragment())
    }

    fun onlineFragment() {
        navigationFragment(OnlineSymptomFragment())
    }

    fun offlineFragment() {
        navigationFragment(OfflineSymptomFragment())
    }

    fun hideNavBottom(boolean: Boolean) {
        val navBottom = binding.bottomNavView
        if (boolean) {
            navBottom.visibility = View.GONE
        } else {
            navBottom.visibility = View.VISIBLE
        }
    }

}