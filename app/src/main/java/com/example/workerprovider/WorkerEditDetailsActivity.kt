package com.example.workerprovider

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workerprovider.databinding.ActivityWorkerEditDetailsBinding
import com.google.firebase.firestore.FirebaseFirestore

class WorkerEditDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkerEditDetailsBinding
    private lateinit var db: FirebaseFirestore
    private var phoneNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerEditDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        val occupations = listOf("Barber","Driver","Guard","Carpenter","Labour",
            "Cook","Electrician","Painter","Plumber","Maid","SalesMan")

        binding.spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, occupations)

        phoneNumber = intent.getStringExtra("phoneNumber")
        loadWorkerDetails(phoneNumber)

        binding.saveChanges.setOnClickListener {
            saveWorkerDetails(phoneNumber)
        }
    }

    private fun loadWorkerDetails(phoneNumber: String?) {
        if (phoneNumber != null) {
            db.collection("workers").document(phoneNumber).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        binding.editTextText.setText(document.getString("name"))
                        binding.editTextText2.setText(document.getString("aadhaarNumber"))
                        binding.editTextText3.setText(document.getString("phoneNumber"))
                        binding.spinner.setSelection((binding.spinner.adapter as ArrayAdapter<String>).getPosition(document.getString("occupation")))
                        binding.editTextText5.setText(document.getString("experience"))
                        binding.editTextText6.setText(document.getString("dailyWages"))
                        binding.editTextText7.setText(document.getString("address"))
                    } else {
                        Toast.makeText(this, "Worker details not found", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error loading worker details: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun saveWorkerDetails(oldPhoneNumber: String?) {
        val name = binding.editTextText.text.toString()
        val aadhaarNumber = binding.editTextText2.text.toString()
        val phoneNumberNew = binding.editTextText3.text.toString()
        val occupation = binding.spinner.selectedItem.toString()
        val experience = binding.editTextText5.text.toString()
        val dailyWages = binding.editTextText6.text.toString()
        val address = binding.editTextText7.text.toString()

        if (oldPhoneNumber != null && name.isNotEmpty() && aadhaarNumber.isNotEmpty() &&
            phoneNumberNew.isNotEmpty() && occupation.isNotEmpty() &&
            experience.isNotEmpty() && dailyWages.isNotEmpty() && address.isNotEmpty()) {

            val worker = hashMapOf(
                "name" to name,
                "aadhaarNumber" to aadhaarNumber,
                "phoneNumber" to phoneNumberNew,
                "occupation" to occupation,
                "experience" to experience,
                "dailyWages" to dailyWages,
                "address" to address
            )

            db.collection("workers").document(phoneNumberNew).set(worker)
                .addOnSuccessListener {
                    db.collection("workers").document(oldPhoneNumber).delete()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Details updated successfully", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, WorkerProfileActivity::class.java)
                            intent.putExtra("phoneNumber", phoneNumberNew)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error deleting old details: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error saving details: ${e.message}", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}