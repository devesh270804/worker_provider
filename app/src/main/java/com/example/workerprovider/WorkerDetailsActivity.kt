package com.example.workerprovider

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workerprovider.databinding.ActivityWorkerDetailsBinding
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class WorkerDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkerDetailsBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        val occupations = listOf("Barber", "Driver", "Guard", "Carpenter", "Labour",
            "Cook", "Electrician", "Painter", "Plumber", "Maid", "SalesMan")

        binding.spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, occupations)

        binding.registerWorker.setOnClickListener {
            saveWorkerDetails()
        }
    }

    private fun saveWorkerDetails() {
        val name = binding.editTextText.text.toString()
        val aadhaarNumber = binding.editTextText2.text.toString()
        val phoneNumber = binding.editTextText3.text.toString()
        val occupation = binding.spinner.selectedItem.toString()
        val experience = binding.editTextText5.text.toString()
        val dailyWages = binding.editTextText6.text.toString()
        val address = binding.editTextText7.text.toString()

        if (name.isNotEmpty() && aadhaarNumber.isNotEmpty() &&
            phoneNumber.isNotEmpty() && occupation.isNotEmpty() &&
            experience.isNotEmpty() && dailyWages.isNotEmpty() && address.isNotEmpty()) {

            getCoordinatesFromAddress(this, address) { coordinate ->
                if (coordinate != null) {
                    val worker = hashMapOf(
                        "name" to name,
                        "aadhaarNumber" to aadhaarNumber,
                        "phoneNumber" to phoneNumber,
                        "occupation" to occupation,
                        "experience" to experience,
                        "dailyWages" to dailyWages,
                        "address" to address,
                        "latitude" to coordinate.latitude,
                        "longitude" to coordinate.longitude
                    )

                    db.collection("workers").document(phoneNumber).set(worker)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Details saved successfully", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, WorkerProfileActivity::class.java)
                            intent.putExtra("phoneNumber", phoneNumber)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error saving details: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                } else {
                    Toast.makeText(this, "Error: Could not get coordinates for the address", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCoordinatesFromAddress(context: Context, address: String, callback: (LatLng?) -> Unit) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocationName(address, 1)
            if (!addresses.isNullOrEmpty()) {
                val location = addresses[0]
                callback(LatLng(location.latitude, location.longitude))
            } else {
                callback(null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callback(null)
        }
    }
}
