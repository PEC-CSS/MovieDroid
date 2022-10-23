package com.pec_acm.moviedroid

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pec_acm.moviedroid.data.Constants
import com.pec_acm.moviedroid.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //github link button
        binding.btnGithubLink.setOnClickListener {

            //open url
            val urlIntent = Intent(Intent.ACTION_VIEW)
            urlIntent.data = Uri.parse(Constants.GITHUB_URL)
            startActivity(urlIntent)
        }

        binding.backBtnAbout .setOnClickListener {
            this.finish()
        }

    }
}