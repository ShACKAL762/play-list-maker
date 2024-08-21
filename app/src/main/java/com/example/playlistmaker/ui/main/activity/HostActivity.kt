package com.example.playlistmaker.ui.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.HostActivityBinding
import com.example.playlistmaker.ui.main.view_model.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class HostActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModel{ parametersOf(this)}
    private lateinit var binding: HostActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = HostActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{_, destination,_ ->
            when(destination.id) {
                R.id.createAlbumFragment -> bottomNavigationView.isVisible = false
                R.id.playerActivity -> bottomNavigationView.isVisible = false
                R.id.playListFragment -> bottomNavigationView.isVisible =false
                else -> bottomNavigationView.isVisible = true
            }

        }
    }
}
