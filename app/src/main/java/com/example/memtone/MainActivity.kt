package com.example.memtone

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.memtone.databinding.ActivityMainBinding
import java.util.concurrent.Executor

const val APP_PREFERENCES_KEY = "APP_PREFERENCES_KEY"
const val  APP_PREFERENCES_PIN = "APP_PREFERENCES_PIN"

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        preferences = getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.mainFragment) {
                binding.toolbar.navigationIcon = null
            } else {
//                binding.toolbar.menu.clear()
            }
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_profile -> {
//                    navController.navigateUp()
                    navController.navigate(R.id.profileFragm)
                    true
                }
                else -> false
            }
        }

        if(isAuthorized()) {
            navController.navigate(R.id.pinCodeFragment)
        } else {
            navController.navigate(R.id.registrationFragment)
        }


    }

    private fun isAuthorized(): Boolean {
        return preferences.getString(APP_PREFERENCES_KEY, "").toString() != ""
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}