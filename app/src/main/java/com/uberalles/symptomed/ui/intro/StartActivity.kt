package com.uberalles.symptomed.ui.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.ActivityStartBinding
import com.uberalles.symptomed.ui.auth.SignInFragment
import com.uberalles.symptomed.ui.auth.SplashFragment
import com.uberalles.symptomed.ui.main.MainActivity

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        checkStatus()
        navigationFragment(SplashFragment())
    }

    fun navigationFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragment_container_start, fragment).addToBackStack(null).commit()
    }

    private fun checkStatus() {
        val user = Firebase.auth.currentUser
        val reference =
            FirebaseDatabase.getInstance("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child(
                user?.uid.toString()
            )

        Log.d("SplashFragment", "User is $user, reference is $reference")

        if (user == null) {
            Handler().postDelayed({
                navigationFragment(SignInFragment())
            }, 3000)
            Log.d("SplashFragment", "User is null")
            return
        }

        reference.child("name").get().addOnCompleteListener { nameTask ->
            val name = nameTask.result.value.toString()
            if (name == "null") {
                //wait 3 second and go to name fragment
                Handler().postDelayed({
                    navigationFragment(NameFragment())
                }, 3000)
                Log.d("SplashFragment", "Name is empty")
                return@addOnCompleteListener
            }

            reference.child("gender").get().addOnCompleteListener { genderTask ->
                val gender = genderTask.result.value.toString()
                if (gender == "null") {
                    Handler().postDelayed({
                        navigationFragment(GenderFragment())
                    }, 3000)
                    Log.d("SplashFragment", "Gender is empty")
                    return@addOnCompleteListener
                }

                reference.child("age").get().addOnCompleteListener { ageTask ->
                    val age = ageTask.result.value.toString()
                    if (age == "null") {
                        Handler().postDelayed({
                            navigationFragment(AgeFragment())
                        }, 3000)
                        Log.d("SplashFragment", "Age is empty")
                        return@addOnCompleteListener
                    }

                    Log.d("SplashFragment", "The field that are present \n$name, $gender, $age")
                    if (user != null) {
                        Handler().postDelayed({
                            val intent = Intent(this, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }, 3000)
                        Log.d("SplashFragment", "User data is not null, goes to main activity")
                    } else {
                        Handler().postDelayed({
                            navigationFragment(SignInFragment())
                        }, 3000)
                        Log.d("SplashFragment", "User is null, goes to sign in fragment")
                    }
                }
            }
        }
    }

}