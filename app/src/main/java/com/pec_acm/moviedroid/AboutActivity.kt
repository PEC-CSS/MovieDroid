package com.pec_acm.moviedroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pec_acm.moviedroid.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}