package com.example.workerprovider

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.workerprovider.databinding.ActivityEmployerProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EmployerProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployerProfileBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_employer_profile)

        db = FirebaseFirestore.getInstance()
        auth =FirebaseAuth.getInstance()

        val userId = auth.currentUser?.uid

        userId?.let {

            db.collection("employers").document(it).get()
                .addOnSuccessListener { document ->
                    if (document != null){
                        val name = "Name : ${document.getString("name")}"
                        val email = "Email : ${document.getString("email")}"
                        val phoneNum = "Phone : ${document.getString("phoneNumber")}"
                        binding.textView.text = name
                        binding.textView2.text = email
                        binding.textView3.text = phoneNum
                    }
                    else {
                        Toast.makeText(this, "No such document", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error getting documents: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }

        binding.deleteAccount.setOnClickListener {
            showDeleteAccountDialog(userId)
        }
    }

    private fun showDeleteAccountDialog(userId: String?) {
        AlertDialog.Builder(this)
            .setTitle("Delete Account")
            .setMessage("Are you sure you want to delete your account?")
            .setPositiveButton("Confirm") { dialog, _ ->
                deleteAccount(userId)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun deleteAccount(userId: String?) {
        userId?.let {
            db.collection("employers").document(it).delete()
                .addOnSuccessListener {
                    auth.currentUser?.delete()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Failed to delete account", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error deleting data: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.back_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.back -> {
                val intent = Intent(this,ViewWorkerActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}