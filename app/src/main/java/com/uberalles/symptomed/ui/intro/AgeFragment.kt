package com.uberalles.symptomed.ui.intro

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentAgeBinding
import com.uberalles.symptomed.ui.main.MainActivity
import java.util.Calendar

class AgeFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentAgeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        helloUser()
        ageResult()
        nextButton()

    }


    private fun nextButton() {
        binding.apply {
            btnNext.setOnClickListener {
                val intent = Intent(activity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    private fun helloUser() {
        val name = arguments?.getString(GenderFragment.EXTRA_NAME)
        val database =
            Firebase.database("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val nameDb = database.reference.child(firebaseAuth.currentUser!!.uid).child("name")

        nameDb.get().addOnSuccessListener {
            if (it.exists()) {
                binding.tvHello.setText("Hello, ${it.value}!")
            }
        }

        binding.tvHello.apply {
            text = "Hello, $name!"
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f).setDuration(1000).setListener(null)
        }
    }

    private fun ageResult() {
        binding.btnCalendar.setOnClickListener {
            selectDate()
        }
    }


    private fun selectDate() {
        val calendar = Calendar.getInstance()
        var cDay = calendar.get(Calendar.DAY_OF_MONTH)
        var cMonth = calendar.get(Calendar.MONTH)
        var cYear = calendar.get(Calendar.YEAR)

        val database =
            Firebase.database("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val genderDb = database.reference.child(firebaseAuth.currentUser!!.uid).child("gender")
        val dobDatabase =
            database.reference.child(firebaseAuth.currentUser!!.uid)
                .child("dateOfBirth")
        val ageDatabase =
            database.reference.child(firebaseAuth.currentUser!!.uid)
                .child("age")


        activity?.let {
            DatePickerDialog(
                it,
                { _, year, month, dayOfMonth ->
                    cDay = dayOfMonth
                    cMonth = month
                    cYear = year

                    val currentYear = Calendar.getInstance()
                        .get(Calendar.YEAR)
                    val age = currentYear - cYear
                    val gender = arguments?.getString(GenderFragment.EXTRA_GENDER)?.lowercase()


                    genderDb.get().addOnSuccessListener {
                        if (it.exists()) {
                            binding.ageResult.apply {
                                visibility = View.VISIBLE
                                text = "You are $age years old ${it.value}!"
                                alpha = 0f
                                visibility = View.VISIBLE
                                animate().alpha(1f).setDuration(1000).setListener(null)
                            }
                        } else {
                            binding.ageResult.apply {
                                visibility = View.VISIBLE
                                text = "You are $age years old $gender!"
                                alpha = 0f
                                visibility = View.VISIBLE
                                animate().alpha(1f).setDuration(1000).setListener(null)
                            }
                        }
                    }
                    binding.btnNext.apply {
                        alpha = 0f
                        visibility = View.VISIBLE
                        animate().alpha(1f).setDuration(1000).setListener(null)
                    }
                    binding.btnCalendar.text = "$cDay/${cMonth + 1}/$cYear"
                    dobDatabase.setValue("$cDay/${cMonth + 1}/$cYear")
                    ageDatabase.setValue(age.toString())

                }, cYear, cMonth, cDay
            ).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}