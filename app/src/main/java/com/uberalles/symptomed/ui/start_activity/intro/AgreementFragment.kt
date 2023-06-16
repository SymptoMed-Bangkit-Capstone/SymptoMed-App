package com.uberalles.symptomed.ui.start_activity.intro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat.animate
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.databinding.FragmentAgreementBinding
import com.uberalles.symptomed.ui.main_activity.MainActivity

class AgreementFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentAgreementBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgreementBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkBoxListener()
        nextButton()
    }

    private fun checkBoxListener() {
        binding.cbAgreement.setOnCheckedChangeListener { _, visible ->
            if (visible) {
                binding.btnNext.apply {
                    alpha = 0f
                    visibility = View.VISIBLE
                    animate().alpha(1f).setDuration(1000).setListener(null)
                }
            } else {
                binding.btnNext.apply {
                    alpha = 1f
                    visibility = View.INVISIBLE
                    animate().alpha(0f).setDuration(1000).setListener(null)
                }
            }
        }
    }

    private fun nextButton() {
        val showBg = binding.clLoading
        binding.btnNext.setOnClickListener {
            if (binding.cbAgreement.isChecked) {
                //animate show alpha background
                showBg.apply {
                    alpha = 0f
                    visibility = View.VISIBLE
                    animate().alpha(1f).setDuration(1000).setListener(null)

                    binding.animationView.playAnimation()
                    binding.animationView.speed = 0.5f

                    postDelayed({
                        val database =
                            Firebase.database("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        val tosDb = database.reference.child(firebaseAuth.currentUser!!.uid).child("tos")
                        tosDb.setValue(true)
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        activity?.finish()
                    }, 3500)
                }

            }
        }
    }
}