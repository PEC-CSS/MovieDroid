package com.pec_acm.moviedroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.databinding.ActivityMainBinding
import com.pec_acm.moviedroid.mainpage.list.ListViewModel

class MainActivity : AppCompatActivity() {
    lateinit var listViewModel: ListViewModel
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
        val listViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        val user = FirebaseAuth.getInstance().currentUser
        listViewModel.addUser(user!!.uid,user.displayName,user.photoUrl?.toString(),user.email)
        listViewModel.getUser(user.uid)
    }



}