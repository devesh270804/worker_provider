package com.example.workerprovider

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.workerprovider.databinding.ActivityEmployerSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EmployerSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployerSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_employer_sign_up)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.button13.setOnClickListener {
            val name = binding.editTextText.text.toString()
            val phoneNumber = binding.editTextText4.text.toString()
            val email = binding.editTextText2.text.toString()
            val password = binding.editTextText3.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && phoneNumber.isNotEmpty()){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            val user = hashMapOf(
                                "name" to name,
                                "phoneNumber" to phoneNumber,
                                "email" to email)
                            userId?.let {
                                db.collection("employers").document(it).set(user)
                                    .addOnSuccessListener {
                                        val intent = Intent(this, ViewWorkerActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(baseContext, "Failed to save user data.", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                        else {
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}