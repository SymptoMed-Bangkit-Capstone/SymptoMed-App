package com.uberalles.symptomed.ui.main.navigation

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
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
import com.google.firebase.storage.FirebaseStorage
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentEditProfileBinding
import com.uberalles.symptomed.ui.main.MainActivity
import com.uberalles.symptomed.utilities.uriToFile
import java.io.File
import java.util.Calendar


class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseReference: DatabaseReference
    private lateinit var firebaseDb: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var photoUri: Uri
    private lateinit var photoPath: String
    private var getFile: File? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDb =
            Firebase.database("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app")
        firebaseReference = firebaseDb.getReference("${firebaseAuth.currentUser?.uid}")
        firebaseStorage = FirebaseStorage.getInstance()
        photoUri = Uri.EMPTY
        photoPath = ""

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
                Glide.with(requireContext())
                    .load(photo)
                    .placeholder(R.drawable.ic_account)
                    .into(binding.ivProfile)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun editProfile() {
        binding.apply {
            var edit = false
            ivEditPhoto.setOnClickListener {
                takePhoto()
            }

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
                val genderEdit = radioGroupGender
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

            btnSave.setOnClickListener {
                val photoUri = Uri.fromFile(File(photoPath))
                val photoRef =
                    firebaseStorage.reference.child("profilePhoto/${firebaseAuth.currentUser?.uid}")
                val uploadTask = photoRef.putFile(photoUri)

                uploadTask.addOnSuccessListener {
                    photoRef.downloadUrl.addOnSuccessListener { uri ->
                        firebaseReference.child("photo").setValue(uri.toString())
                    }
                    Toast.makeText(requireContext(), "Photo uploaded", Toast.LENGTH_SHORT).show()
                }
                uploadTask.addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to upload photo", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }

    private fun takePhoto() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Option")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Take Photo" -> {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    val file = File(
                        requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "profile.jpg"
                    )
                    photoPath = file.absolutePath
                    val uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.uberalles.symptomed.utilities",
                        file
                    )
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                    launcherIntentCamera.launch(intent)
                    binding.btnSave.visibility = View.VISIBLE
                }

                options[item] == "Choose from Gallery" -> {
                    val galleryIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    launcherIntentGallery.launch(galleryIntent)
                    binding.btnSave.visibility = View.VISIBLE
                }

                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()

    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(photoPath)

            myFile.let { file ->
                getFile = file
                binding.ivProfile.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private val launcherIntentGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                it.data?.data?.let { uri ->
                    val file = uriToFile(uri, requireContext())
                    run {
                        photoUri = uri
                        photoPath = file.absolutePath
                        Glide.with(requireContext())
                            .load(photoUri)
                            .into(binding.ivProfile)
                    }
                }
            }
        }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity().baseContext,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        const val REQUEST_CODE_PERMISSIONS = 10
    }
}
