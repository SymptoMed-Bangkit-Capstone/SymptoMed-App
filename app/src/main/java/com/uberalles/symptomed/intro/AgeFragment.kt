package com.uberalles.symptomed.intro

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.databinding.FragmentAgeBinding
import com.uberalles.symptomed.ui.MainActivity
import java.util.Calendar

class AgeFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentAgeBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragmentArguments: Bundle

    companion object {
        private const val EXTRA_AGE = "extra_age"
        private const val EXTRA_NAME = "extra_name"
        private const val EXTRA_GENDER = "extra_gender"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAgeBinding.inflate(layoutInflater, container, false)
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentArguments = Bundle()
        firebaseAuth = FirebaseAuth.getInstance()

        helloUser()
        ageResult()
        nextButton()

    }


    private fun nextButton() {
        binding.apply {
            btnNext.setOnClickListener {
                val database =
                    Firebase.database("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app/")
                val ageDatabase =
                    database.reference.child(firebaseAuth.currentUser!!.uid)
                        .child("age")

                val name = arguments?.getString(NameFragment.EXTRA_NAME)
                val gender = arguments?.getString(GenderFragment.EXTRA_GENDER)
                val age = fragmentArguments.getInt(EXTRA_AGE, 0)

                //save to shared preferences
                val editor = sharedPreferences.edit()
                editor.putString("age", age.toString())
                editor.apply()
                Toast.makeText(
                    requireContext(),
                    sharedPreferences.getString("age", ""),
                    Toast.LENGTH_SHORT
                ).show()

                //save to firebase
                ageDatabase.setValue(age.toString())

                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra(EXTRA_AGE, age)
                intent.putExtra(EXTRA_NAME, name)
                intent.putExtra(EXTRA_GENDER, gender)
                startActivity(intent)
            }
        }
    }


    private fun helloUser() {
        val name = arguments?.getString(GenderFragment.EXTRA_NAME)

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
        var calendar = Calendar.getInstance()
        var cDay = calendar.get(Calendar.DAY_OF_MONTH)
        var cMonth = calendar.get(Calendar.MONTH)
        var cYear = calendar.get(Calendar.YEAR)

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

                    fragmentArguments.putInt(EXTRA_AGE, age)

                    binding.ageResult.apply {
                        visibility = View.VISIBLE
                        text = "You are $age years old $gender!"
                        alpha = 0f
                        visibility = View.VISIBLE
                        animate().alpha(1f).setDuration(1000).setListener(null)
                        //put age in bundle
                    }
                    binding.btnNext.apply {
                        alpha = 0f
                        visibility = View.VISIBLE
                        animate().alpha(1f).setDuration(1000).setListener(null)
                    }
                    binding.btnCalendar.text = "$cDay/${cMonth + 1}/$cYear"
                    val database =
                        Firebase.database("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    val dobDatabase =
                        database.reference.child(firebaseAuth.currentUser!!.uid)
                            .child("dateOfBirth")
                    dobDatabase.setValue("$cDay/${cMonth + 1}/$cYear")
                }, cYear, cMonth, cDay
            ).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}