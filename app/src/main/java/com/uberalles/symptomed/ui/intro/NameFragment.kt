package com.uberalles.symptomed.ui.intro

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentNameBinding

class NameFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentNameBinding? = null

    private val binding get() = _binding!!
    private lateinit var bundle: Bundle

    companion object {
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNameBinding.inflate(layoutInflater, container, false)
        sharedPreferences =
            requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bundle = Bundle()
        firebaseAuth = FirebaseAuth.getInstance()

        nameResult()
        nextButton()
        backButton()
    }

    private fun backButton() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    AlertDialog.Builder(requireContext()).setTitle("Are you sure?")
                        .setMessage("Do you want to exit the app?")
                        .setPositiveButton("Yes") { _, _ ->
                            activity?.finish()
                        }.setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }.create().show()
                }
            })
    }


    private fun nameResult() {
        binding.apply {
            tvName.addTextChangedListener {
                if (it.toString().isEmpty()) {
                    binding.button.apply {
                        visibility = View.INVISIBLE
                    }
                } else {
                    binding.button.apply {
                        alpha = 0f
                        visibility = View.VISIBLE
                        animate().alpha(1f).setDuration(1000).setListener(null)
                    }
                }
            }
        }
    }

    private fun nextButton() {
        binding.apply {
            button.setOnClickListener {
                val name = binding.tvName.text.toString()
                //save to shared preferences
                val editor = sharedPreferences.edit()
                editor.putString("name", name)
                editor.apply()
                Toast.makeText(
                    requireContext(), sharedPreferences.getString("name", ""), Toast.LENGTH_SHORT
                ).show()

                //save to firebase
                val database =
                    Firebase.database("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app/")
                val nameDb = database.reference.child(firebaseAuth.currentUser!!.uid).child("name")
                nameDb.setValue(name)

                bundle.putString(EXTRA_NAME, binding.tvName.text.toString())
                findNavController().navigate(R.id.action_nameFragment_to_genderFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
