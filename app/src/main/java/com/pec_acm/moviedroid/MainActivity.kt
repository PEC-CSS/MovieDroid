package com.pec_acm.moviedroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.pec_acm.moviedroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        //Implementing Bottom Navigation View
        val bottomNavigationView = binding!!.bottomNavBar
        val navController = findNavController(R.id.main_page_fragment_container)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.searchFragment,R.id.listFragment))
        setupActionBarWithNavController(navController,appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }

}