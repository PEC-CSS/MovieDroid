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
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.databinding.ActivityMainBinding
import com.pec_acm.moviedroid.mainpage.list.ListViewModel
import com.pec_acm.moviedroid.mainpage.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var authListener: FirebaseAuth.AuthStateListener
    private val logoutObserver: MutableLiveData<FirebaseAuth> = MutableLiveData()
    private var isBottomNavHidden = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Implementing Bottom Navigation View
        val navController = findNavController(R.id.main_page_fragment_container)

        binding.bottomNavBar.setupWithNavController(navController)

        val bottomNavDestinations = setOf(
            R.id.homeFragment,
            R.id.searchFragment,
            R.id.listFragment
        )

        // Implementing Navigation drawer
        val appBarConfiguration = AppBarConfiguration(
            bottomNavDestinations,
            binding.dlParent
        )
        binding.toolBar.setupWithNavController(navController, appBarConfiguration)

        val listViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        val user = FirebaseAuth.getInstance().currentUser

        listViewModel.addUser(user!!.uid, user.displayName, user.photoUrl?.toString(), user.email)
        listViewModel.getUser(user.uid)

        val headerView: View = binding.sideNavBar.getHeaderView(0)

        val tvUserName: TextView = headerView.findViewById(R.id.tv_user_name)
        val tvEmailId: TextView = headerView.findViewById(R.id.tv_email_id)
        val ivProfilePic: ImageView = headerView.findViewById(R.id.iv_profile_picture)

        tvUserName.text = user.displayName
        tvEmailId.text = user.email

        Glide.with(this)
            .load(user.photoUrl)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .into(ivProfilePic)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in bottomNavDestinations) {
                showBottomNavigation()
                showToolBar()
            } else {
                binding.bottomNavBar.visibility = View.GONE
                binding.toolBar.visibility = View.GONE
            }
        }

        binding.sideNavBar.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_show_logout_dialog -> {
                    navController.navigate(R.id.show_log_out_dialog)
                }

                R.id.item_move_to_profile_page -> {
                    //move to profile activity
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
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

    fun hideBottomNavigation() {
        binding.bottomNavBar
            .animate()
            .translationY(binding.bottomNavBar.height.toFloat())
            .setDuration(300)

    }

    fun showBottomNavigation() {
        binding.bottomNavBar.visibility = View.VISIBLE
        binding.bottomNavBar
            .animate()
            .translationY(0f)
            .setDuration(300)
    }

    fun showToolBar() {
        binding.bottomNavBar.visibility = View.VISIBLE
        binding.toolBar.visibility = View.VISIBLE
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

    override fun onBackPressed() {
        val navHost = supportFragmentManager.findFragmentById(R.id.main_page_fragment_container)
        val fragment = navHost?.childFragmentManager?.primaryNavigationFragment
        when (fragment) {
            is SearchFragment -> {
                if (fragment.searchText.isIconified) {
                    super.onBackPressed()
                } else {
                    fragment.searchText.isIconified = true
                }
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}