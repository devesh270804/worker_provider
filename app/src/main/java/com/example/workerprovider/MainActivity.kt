package com.example.workerprovider

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.workerprovider.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.buttonFirst.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        val animationTop = AnimationUtils.loadAnimation(this, R.anim.top_to_middle)
        val animationBottom = AnimationUtils.loadAnimation(this, R.anim.bottom_to_middle)

        binding.txtTop.startAnimation(animationTop)
        binding.txtBottom.startAnimation(animationBottom)
    }
}