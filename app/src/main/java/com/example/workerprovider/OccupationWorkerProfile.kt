package com.example.workerprovider

data class OccupationWorkerProfile(
    val name:String = "",
    val phoneNumber : String = "",
    val dailyWages : String = "",
    val experience : String = "",
    val address : String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    var distance: Float = 0.0f)
