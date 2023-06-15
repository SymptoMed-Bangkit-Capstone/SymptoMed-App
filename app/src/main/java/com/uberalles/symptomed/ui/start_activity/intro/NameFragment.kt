package com.uberalles.symptomed.ui.start_activity.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.databinding.FragmentNameBinding
import com.uberalles.symptomed.ui.start_activity.StartActivity

class NameFragment : Fragment() {

//    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentNameBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        nameResult()
        nextButton()
        backButton()
    }

    private fun backButton() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    AlertDialog.Builder(requireContext()).setTitle("Apakah anda yakin untuk keluar?")
                        .setMessage("Proses pendaftaran masih dapat dilanjutkan")
                        .setPositiveButton("Ya") { _, _ ->
                            activity?.finish()
                        }.setNegativeButton("Tidak") { dialog, _ ->
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
                val database =
                    Firebase.database("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app/")
                val nameDb = database.reference.child(firebaseAuth.currentUser!!.uid).child("name")
                nameDb.setValue(name)

                (activity as StartActivity).navigationFragment(GenderFragment())

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
