package com.example.workerprovider.occupations

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

class MaidActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var workerListView: RecyclerView
    private lateinit var workerAdapter: WorkerAdapter
    private val maids = mutableListOf<OccupationWorkerProfile>()
    private val LOCATION_PERMISSION_REQUEST_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guard)

        firestore = FirebaseFirestore.getInstance()
        workerListView = findViewById(R.id.recyclerView)
        workerAdapter = WorkerAdapter(maids)
        workerListView.layoutManager = LinearLayoutManager(this)
        workerListView.adapter = workerAdapter

        fetchMaids()

    }

    private fun fetchMaids(){
        firestore.collection("workers").whereEqualTo("occupation","Maid")
            .get().addOnSuccessListener { documents ->
                for (document in documents){
                    val maid  = document.toObject(OccupationWorkerProfile::class.java)
                    maids.add(maid)
                }
                getCurrentLocation{ loaction ->
                    if (loaction != null){
                        sortAndDisplayMaid(loaction)
                    }
                  else{
                      Toast.makeText(this,"Unable to get the current location",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.w("MaidActivity", " Error getting documents : ",e)
            }
    }

    private fun getCurrentLocation(callback : (Location?) -> Unit){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
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
    private fun sortAndDisplayMaid(employerLocation: Location) {
        maids.forEach {
            it.distance = calculateDistance(employerLocation.latitude, employerLocation.longitude, it.latitude, it.longitude)
        }
        maids.sortBy { it.distance }
        workerAdapter.notifyDataSetChanged()
    }
}