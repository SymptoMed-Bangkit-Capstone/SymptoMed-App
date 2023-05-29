package com.uberalles.symptomed.intro

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uberalles.symptomed.databinding.FragmentAgeBinding
import com.uberalles.symptomed.ui.MainActivity
import java.util.Calendar

class AgeFragment : Fragment() {
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentArguments = Bundle()

        helloUser()
        ageResult()
        nextButton()

    }



    private fun nextButton() {
        binding.apply {
            btnNext.setOnClickListener {

                val name = arguments?.getString(NameFragment.EXTRA_NAME)
                val gender = arguments?.getString(GenderFragment.EXTRA_GENDER)
                val age = fragmentArguments.getInt(EXTRA_AGE,0)


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

                }, cYear, cMonth, cDay
            ).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}