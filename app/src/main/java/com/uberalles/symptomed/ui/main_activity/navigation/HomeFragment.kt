package com.uberalles.symptomed.ui.main_activity.navigation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentHomeBinding
import com.uberalles.symptomed.ui.main_activity.prediction.OfflineSymptomFragment
import com.uberalles.symptomed.ui.main_activity.prediction.OnlineSymptomFragment


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        offlineSymptom()
        onlineSymptom()
        emergencyCall()
    }

    private fun emergencyCall() {
        binding.ivEmergencyCall.setOnClickListener {
            val options = arrayOf<CharSequence>("Ya", "Tidak")
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Apakah Anda yakin ingin menghubungi 122?")
            builder.setItems(options) { dialog, item ->
                if (options[item] == "Ya") {
                    callEmergency()
                } else if (options[item] == "Tidak") {
                    dialog.dismiss()
                }
            }
            builder.show()
        }
    }

    private fun callEmergency() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:" + "122")
            startActivity(callIntent)
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun onlineSymptom() {
        binding.ivOnlinePrediction.setOnClickListener {
            fragmentNav(OnlineSymptomFragment())
        }
    }

    private fun offlineSymptom() {
        binding.ivOfflinePrediction.setOnClickListener {
            fragmentNav(OfflineSymptomFragment())
        }
    }

    private fun fragmentNav(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction =
            fragmentManager.beginTransaction().replace(R.id.fragment_container_main, fragment)
        fragmentTransaction.commit()
    }

    companion object {
        private const val CALL_PERMISSION_REQUEST_CODE = 10
    }

}