package com.example.workerprovider

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workerprovider.databinding.ActivityWorkerRegisterBinding
import com.google.firebase.firestore.FirebaseFirestore

class WorkerRegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWorkerRegisterBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkerRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        binding.createProfileButton.setOnClickListener {
            val intent= Intent(this,WorkerDetailsActivity::class.java)
            startActivity(intent)
        }

        binding.goToProfileButton.setOnClickListener {
            val phoneNumber = binding.editTextPhoneNumber.text.toString()
            if(phoneNumber.isNotEmpty()){

                val intent = Intent(this,WorkerProfileActivity::class.java)
                intent.putExtra("phoneNumber",phoneNumber)
                startActivity(intent)

            }else{
                Toast.makeText(this,"Please enter your phone number",Toast.LENGTH_SHORT).show()

            }

        }

    }
}


