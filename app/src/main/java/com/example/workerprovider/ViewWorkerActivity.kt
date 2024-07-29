package com.example.workerprovider

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.example.workerprovider.occupations.BarberActivity
import com.example.workerprovider.occupations.CarpenterActivity
import com.example.workerprovider.occupations.CookActivity
import com.example.workerprovider.occupations.DriverActivity
import com.example.workerprovider.occupations.ElectricianActivity
import com.example.workerprovider.occupations.GuardActivity
import com.example.workerprovider.occupations.LabourActivity
import com.example.workerprovider.occupations.MaidActivity
import com.example.workerprovider.occupations.PainterActivity
import com.example.workerprovider.occupations.PlumberActivity
import com.example.workerprovider.occupations.SalesManActivity
import com.google.firebase.auth.FirebaseAuth

class ViewWorkerActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_worker)

        auth = FirebaseAuth.getInstance()

        val gridView : GridView = findViewById(R.id.gridView)
        val occupation1 = Occupation("Barber",R.drawable.barber)
        val occupation2 = Occupation("Carpenter",R.drawable.carpenter)
        val occupation3 = Occupation("Cook",R.drawable.cook)
        val occupation4 = Occupation("Driver",R.drawable.driver)
        val occupation5 = Occupation("Electrician",R.drawable.electrician)
        val occupation6 = Occupation("Guard",R.drawable.guard)
        val occupation7 = Occupation("Labour",R.drawable.labor)
        val occupation8 = Occupation("Maid",R.drawable.maid)
        val occupation9 = Occupation("Painter",R.drawable.painter)
        val occupation10 = Occupation("Plumber",R.drawable.plumber)
        val occupation11 = Occupation("SalesMan",R.drawable.salesman)

        val ocItems = listOf(occupation1,occupation2,occupation3,occupation4,occupation5,
            occupation6,occupation7,occupation8,occupation9,occupation10,occupation11)

        val adapter = MyAdapter(this,ocItems)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->
            val selectedOccupation = adapter.getItem(position)

            if (selectedOccupation?.occupation == "Barber"){
                val intent = Intent(this, BarberActivity::class.java)
                startActivity(intent)
            }
            if (selectedOccupation?.occupation == "Carpenter"){
                val intent = Intent(this, CarpenterActivity::class.java)
                startActivity(intent)
            }
            if (selectedOccupation?.occupation == "Cook"){
                val intent = Intent(this, CookActivity::class.java)
                startActivity(intent)
            }
            if (selectedOccupation?.occupation == "Driver"){
                val intent = Intent(this, DriverActivity::class.java)
                startActivity(intent)
            }
            if (selectedOccupation?.occupation == "Electrician"){
                val intent = Intent(this, ElectricianActivity::class.java)
                startActivity(intent)
            }
            if(selectedOccupation?.occupation == "Guard"){
                val intent = Intent(this, GuardActivity::class.java)
                startActivity(intent)
            }
            if (selectedOccupation?.occupation == "Labour"){
                val intent = Intent(this, LabourActivity::class.java)
                startActivity(intent)
            }
            if (selectedOccupation?.occupation == "Maid"){
                val intent = Intent(this, MaidActivity::class.java)
                startActivity(intent)
            }
            if (selectedOccupation?.occupation == "Painter"){
                val intent = Intent(this, PainterActivity::class.java)
                startActivity(intent)
            }
            if (selectedOccupation?.occupation == "Plumber"){
                val intent = Intent(this, PlumberActivity::class.java)
                startActivity(intent)
            }
            if (selectedOccupation?.occupation == "SalesMan"){
                val intent = Intent(this, SalesManActivity::class.java)
                startActivity(intent)
            }

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.employe_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.logOut -> {
                auth.signOut()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.backToLogin -> {
                val intent = Intent(this,RegistrationActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.profile -> {
                val intent = Intent(this,EmployerProfileActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




}