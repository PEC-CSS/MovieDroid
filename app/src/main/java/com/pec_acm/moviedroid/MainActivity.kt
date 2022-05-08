package com.pec_acm.moviedroid


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.databinding.ActivityMainBinding
import com.pec_acm.moviedroid.mainpage.list.ListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var authListener: FirebaseAuth.AuthStateListener
    private val logoutObserver: MutableLiveData<FirebaseAuth> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Implementing Bottom Navigation View
        val navController = findNavController(R.id.main_page_fragment_container)
        binding.bottomNavBar.setupWithNavController(navController)

        // Implementing Navigation drawer
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.searchFragment, R.id.listFragment),
            binding.dlParent
        )
        binding.toolBar.setupWithNavController(navController, appBarConfiguration)

        val listViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        val user = FirebaseAuth.getInstance().currentUser

        listViewModel.addUser(user!!.uid, user.displayName, user.photoUrl?.toString(), user.email)
        listViewModel.getUser(user.uid)

        val headerView: View = binding.sideNavBar.getHeaderView(0)

        val tvUserName: TextView = headerView.findViewById(R.id.tvUserName)
        val tvEmailId: TextView = headerView.findViewById(R.id.tvEmailId)
        val ivProfilePic: ImageView = headerView.findViewById(R.id.ivProfilePicture)

        tvUserName.text = user.displayName
        tvEmailId.text = user.email

        Glide.with(this)
            .load(user.photoUrl)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .into(ivProfilePic)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.detailFragment) {
                binding.bottomNavBar.visibility = View.GONE
            } else {
                binding.bottomNavBar.visibility = View.VISIBLE
            }
        }

        binding.sideNavBar.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_show_logout_dialog -> {
                    Navigation
                        .findNavController(this, R.id.main_page_fragment_container)
                        .navigate(R.id.show_log_out_dialog)
                }

                else -> {
                    Snackbar.make(
                        binding.root,
                        R.string.some_awesome_feature_is_on_its_way,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            binding.dlParent.close()
            return@setNavigationItemSelectedListener true
        }

        authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (null == firebaseAuth.currentUser) {
                logoutObserver.value = firebaseAuth
            }
        }

        FirebaseAuth.getInstance().addAuthStateListener(authListener)

        logoutObserver.observe(this) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        FirebaseAuth.getInstance().removeAuthStateListener(authListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}