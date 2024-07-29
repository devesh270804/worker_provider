package com.example.workerprovider

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.workerprovider.databinding.ActivityRegistrationBinding
class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_registration)


        binding.buttonEmployer.setOnClickListener {
            val intent = Intent(this,EmployerLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonLabour.setOnClickListener {
            val intent = Intent(this,WorkerRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}