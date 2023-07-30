package com.technic.spendtrace

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.technic.spendtrace.database.PurchaseDatabase
import com.technic.spendtrace.database.PurchaseViewModel
import com.technic.spendtrace.database.PurchaseViewModelFactory
import com.technic.spendtrace.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val purchaseViewModel: PurchaseViewModel by viewModels {
        PurchaseViewModelFactory((application as PurchaseApplication).repository)
    }

    val pViewModel
        get() = purchaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_recent, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

}