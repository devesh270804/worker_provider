package com.example.workerprovider

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.workerprovider.databinding.ActivityEmployerLoginBinding
import com.google.firebase.auth.FirebaseAuth

class EmployerLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployerLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employer_login)

        auth = FirebaseAuth.getInstance()

        binding.button4.setOnClickListener {
            val email = binding.editTextText2.text.toString()
            val password = binding.editTextText3.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, ViewWorkerActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(baseContext, "Authentication Failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(baseContext, "Please enter email and password", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.button5.setOnClickListener {
            val intent = Intent(this,EmployerSignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.textViewForgotPassword.setOnClickListener {
            val intent = Intent(this,EmployerForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if(user!=null){
            val intent = Intent(this, ViewWorkerActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
