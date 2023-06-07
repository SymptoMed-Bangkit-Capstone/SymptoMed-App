package com.uberalles.symptomed.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.ActivityMainBinding
import com.uberalles.symptomed.intro.StartActivity
import com.uberalles.symptomed.viewmodel.MainViewModel

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

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragment = SymptomFragment()
        fragmentManager.beginTransaction().replace(R.id.fragment_container_main, fragment)
            .commit()

        logoutButton()
        toolbarText()

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
        val title = sharedPreferences.getString("name", "")
        binding.apply {
            toolbar.title = "Hello, $title!"
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
            .setNegativeButton("No") { dialogInterface, it ->
                dialogInterface.cancel()
            }
            .show()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}