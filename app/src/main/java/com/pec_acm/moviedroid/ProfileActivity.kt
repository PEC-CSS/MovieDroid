package com.pec_acm.moviedroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pec_acm.moviedroid.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}