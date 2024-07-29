package com.example.workerprovider

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.workerprovider.databinding.ActivityEmployerForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class EmployerForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployerForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_employer_forgot_password)

        auth = FirebaseAuth.getInstance()

        binding.buttonResetPassword.setOnClickListener {
            val email = binding.resetEmailEditText.text.toString()
            if (email.isEmpty()){
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this,"Link sent to your registered email",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this,"Error : ${task.exception?.message}",Toast.LENGTH_LONG).show()
                    }
                }
        }

        binding.buttonBackToLogIn.setOnClickListener {
            val intent = Intent(this,EmployerLoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}