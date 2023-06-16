package com.uberalles.symptomed.ui.main_activity.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentProfileBinding
import com.uberalles.symptomed.ui.main_activity.MainActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseReference: DatabaseReference
    private lateinit var firebaseDb: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDb =
            Firebase.database("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app")
        firebaseReference = firebaseDb.getReference("${firebaseAuth.currentUser?.uid}")


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editProfile()
        logout()
        val nameDatabase = firebaseReference.child("name")
        val genderDatabase = firebaseReference.child("gender")
        val ageDatabase = firebaseReference.child("age")
        val dobDatabase = firebaseReference.child("dateOfBirth")
        val emailDatabase = firebaseReference.child("email")
        val photoDatabase = firebaseReference.child("photo")

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
                if (gender == "Male") {
                    binding.tvGender.text = "Laki-laki"
                } else {
                    binding.tvGender.text = "Perempuan"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        })

        ageDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val age = snapshot.getValue(String::class.java)
                Log.d("ProfileFragment", "onDataChange: $age")
                binding.tvAge.text = "$age tahun"
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

        photoDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val photo = snapshot.getValue(String::class.java)
                Log.d("ProfileFragment", "onDataChange: $photo")
                context?.let {
                    Glide.with(it.applicationContext)
                        .load(photo)
                        .placeholder(R.drawable.ic_account)
                        .into(binding.ivProfile)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun logout() {
        binding.btnLogout.setOnClickListener {
            (activity as MainActivity).logout()
        }
    }

    private fun editProfile() {
        binding.btnEditProfile.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_main, EditProfileFragment())
            fragmentTransaction.commit()
        }
    }

}
