package com.uberalles.symptomed.ui.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentEditProfileBinding
import java.util.Calendar

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseReference: DatabaseReference
    private lateinit var firebaseDb: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDb =
            Firebase.database("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app")
        firebaseReference = firebaseDb.getReference("${firebaseAuth.currentUser?.uid}")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editProfile()

        val nameDatabase = firebaseReference.child("name")
        val genderDatabase = firebaseReference.child("gender")
        val ageDatabase = firebaseReference.child("age")
        val dobDatabase = firebaseReference.child("dateOfBirth")
        val emailDatabase = firebaseReference.child("email")

        nameDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.getValue(String::class.java)
                Log.d("ProfileFragment", "onDataChange: $name")
                binding.tvName.text = name
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        })

        genderDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val gender = snapshot.getValue(String::class.java)
                Log.d("ProfileFragment", "onDataChange: $gender")
                binding.tvGender.text = gender
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        })

        ageDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val age = snapshot.getValue(String::class.java)
                Log.d("ProfileFragment", "onDataChange: $age")
                binding.tvAge.text = "$age years old"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        })

        dobDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dob = snapshot.getValue(String::class.java)
                Log.d("ProfileFragment", "onDataChange: $dob")
                binding.tvDob.text = dob
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        })

        emailDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val email = snapshot.getValue(String::class.java)
                Log.d("ProfileFragment", "onDataChange: $email")
                binding.tvEmail.text = email
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun editProfile() {
        binding.apply {

            var edit = false
            val name = tvName
            val nameEdit = inputName

            ivEditName.setOnClickListener {
                if (edit) {
                    ivEditName.setImageResource(R.drawable.ic_edit)
                    name.visibility = View.VISIBLE
                    nameEdit.visibility = View.GONE

                    if (nameEdit.text.toString().isEmpty()) {
                        Toast.makeText(
                            requireContext(), "Name cannot be empty", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        firebaseReference.child("name").setValue(nameEdit.text.toString())
                    }
                } else {
                    ivEditName.setImageResource(android.R.drawable.ic_menu_save)

                    name.visibility = View.GONE
                    nameEdit.visibility = View.VISIBLE
                    nameEdit.hint = name.text
                }
                edit = !edit

            }

            ivEditGender.setOnClickListener {
                val gender = tvGender
                var genderEdit = radioGroupGender
                if (edit) {
                    ivEditGender.setImageResource(R.drawable.ic_edit)
                    gender.visibility = View.VISIBLE
                    genderEdit.visibility = View.GONE

                    if (radioButtonMale.isChecked) {
                        firebaseReference.child("gender").setValue("Male")
                    } else if (radioButtonFemale.isChecked) {
                        firebaseReference.child("gender").setValue("Female")
                    }
                } else {
                    ivEditGender.setImageResource(android.R.drawable.ic_menu_save)

                    gender.visibility = View.GONE
                    genderEdit.visibility = View.VISIBLE
                }
                edit = !edit
            }

            ivEditDob.setOnClickListener {
                val calendar = Calendar.getInstance()
                var cDay = calendar.get(Calendar.DAY_OF_MONTH)
                var cMonth = calendar.get(Calendar.MONTH)
                var cYear = calendar.get(Calendar.YEAR)

                activity?.let {
                    DatePickerDialog(
                        it, { _, year, month, dayOfMonth ->
                            cDay = dayOfMonth
                            cMonth = month
                            cYear = year

                            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                            val age = currentYear - cYear

                            firebaseReference.child("age").setValue(age.toString())
                            firebaseReference.child("dateOfBirth").setValue("$cDay/$cMonth/$cYear")
                        }, cYear, cMonth, cDay
                    ).show()
                }

            }

            btnBack.setOnClickListener {
                (activity as MainActivity).backToProfile()
            }
        }
    }
}
