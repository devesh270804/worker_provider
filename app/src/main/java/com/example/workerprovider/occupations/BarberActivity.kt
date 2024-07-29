package com.example.workerprovider.occupations

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workerprovider.OccupationWorkerProfile
import com.example.workerprovider.R
import com.example.workerprovider.WorkerAdapter
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore

class BarberActivity:AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var workerListView: RecyclerView
    private lateinit var workerAdapter: WorkerAdapter
    private val barbers = mutableListOf<OccupationWorkerProfile>()
    private var LOCATION_PERMISSION_REQUEST_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guard)

        firestore = FirebaseFirestore.getInstance()
        workerListView = findViewById(R.id.recyclerView)
        workerAdapter = WorkerAdapter(barbers)
        workerListView.layoutManager = LinearLayoutManager(this)
        workerListView.adapter = workerAdapter

        fetchBarbers()

    }

    private fun fetchBarbers(){
        firestore.collection("workers").whereEqualTo("occupation","Barber")
            .get().addOnSuccessListener { documents ->
                for (document in documents){
                    val barber  = document.toObject(OccupationWorkerProfile::class.java)
                    barbers.add(barber)
                }
                getCurrentLocation{ location ->
                    if (location != null){
                        sortAndDisplayBarbers(location)
                    }else{
                        Toast.makeText(this,"Unable to fetch current location ",Toast.LENGTH_SHORT).show()
                    }

                }
            }
            .addOnFailureListener { e ->
                Log.w("BarberActivity", " Error getting documents : ",e)
            }
    }
    private fun getCurrentLocation(callback: (Location?) -> Unit) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                callback(location)
            }
        }
    }

    private fun calculateDistance(startLat: Double, startLng: Double, endLat: Double, endLng: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(startLat, startLng, endLat, endLng, results)
        return results[0]
    }

    private fun sortAndDisplayBarbers(employerLocation: Location) {
        barbers.forEach {
            it.distance = calculateDistance(employerLocation.latitude, employerLocation.longitude, it.latitude, it.longitude)
        }
        barbers.sortBy { it.distance }
        workerAdapter.notifyDataSetChanged()
    }

}