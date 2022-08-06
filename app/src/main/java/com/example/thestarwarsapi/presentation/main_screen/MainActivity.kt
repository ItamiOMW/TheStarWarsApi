package com.example.thestarwarsapi.presentation.main_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.thestarwarsapi.R
import com.example.thestarwarsapi.databinding.ActivityMainBinding
import com.example.thestarwarsapi.presentation.favorites_screen.FavoritesFragment
import com.example.thestarwarsapi.presentation.search_screen.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnBottomNavigationListener()
    }

    private fun setOnBottomNavigationListener() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_search -> {
                    launchFragment(SearchFragment.getInstance())
                    true
                }
                R.id.item_favorite -> {
                    launchFragment(FavoritesFragment.getInstance())
                    true
                }
                else -> throw RuntimeException("Unknown itemId: ${it.itemId}")
            }
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }
}