package com.example.workerprovider

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workerprovider.databinding.ActivityWorkerProfileBinding
import com.google.firebase.firestore.FirebaseFirestore

class WorkerProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkerProfileBinding
    private var phoneNumber: String? = null
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        phoneNumber = intent.getStringExtra("phoneNumber")
        loadWorkerDetails(phoneNumber)

        binding.editAccount.setOnClickListener {
            val intent = Intent(this, WorkerEditDetailsActivity::class.java)
            intent.putExtra("phoneNumber", phoneNumber)
            startActivity(intent)
        }

        binding.deleteAccount.setOnClickListener {
            showDeleteAccountDialog(phoneNumber)
        }
    }

    private fun loadWorkerDetails(phoneNumber: String?) {
        if (phoneNumber != null) {
            db.collection("workers").document(phoneNumber).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {

                        val name = "Name   :  ${document.getString("name")}"
                        val occupation = "Occupation  :  ${document.getString("occupation")}"
                        val wage = "Wage   :   Rs  ${document.getString("dailyWages")} "
                        val phoneNumber2 = "Phone number  :  ${document.getString("phoneNumber")}"
                        val experience = "Experience   :  ${document.getString("experience")} " + " years"
                        val aadhaarNumber = "Aadhaar number  :  ${document.getString("aadhaarNumber")}"
                        val address = "Address  :   ${document.getString("address")}"

                        val occupation2 = document.getString("occupation")

                        binding.workerName.text = name
                        binding.workerOccupation.text = occupation
                        binding.workerWage.text = wage
                        binding.workerPhoneNumber.text = phoneNumber2
                        binding.workerExperience.text = experience
                        binding.workerAadhaarNumber.text = aadhaarNumber
                        binding.workerAddress.text = address

                        occupation2?.let {
                            val imageRes = getImageResourceForOccupation(it)
                            binding.imageView.setImageResource(imageRes)
                        }


                    }else{
                        Toast.makeText(this, "Worker details not found", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error loading worker details: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun showDeleteAccountDialog(phoneNumber: String?) {
        AlertDialog.Builder(this)
            .setTitle("Delete Account")
            .setMessage("Are you sure you want to delete your account?")
            .setPositiveButton("Confirm") { dialog, _ ->
                deleteWorkerAccount(phoneNumber)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }


    private fun deleteWorkerAccount(phoneNumber: String?) {
        phoneNumber?.let {
            db.collection("workers").document(it).delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error deleting data: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun getImageResourceForOccupation(occupation: String): Int {
        return when (occupation) {
            "Barber" -> R.drawable.barber
            "Driver" -> R.drawable.driver
            "Guard" -> R.drawable.guard
            "Carpenter" -> R.drawable.carpenter
            "Labour" -> R.drawable.labor
            "Cook" -> R.drawable.cook
            "Electrician" -> R.drawable.electrician
            "Painter" -> R.drawable.painter
            "Plumber" -> R.drawable.plumber
            "Maid" -> R.drawable.maid
            "SalesMan" -> R.drawable.salesman
            else -> R.drawable.profile_image
        }
    }
}