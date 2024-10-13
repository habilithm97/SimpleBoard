package com.example.simpleboard.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.simpleboard.R
import com.example.simpleboard.databinding.ActivityMainBinding
import com.example.simpleboard.ui.fragment.AddFragment
import com.example.simpleboard.ui.fragment.ChatFragment
import com.example.simpleboard.ui.fragment.HomeFragment
import com.example.simpleboard.ui.fragment.ProfileFragment
import com.example.simpleboard.ui.fragment.SearchFragment

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val menuItems = listOf(
        R.id.nav_home,
        R.id.nav_search,
        R.id.nav_add,
        R.id.nav_profile,
        R.id.nav_chat
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        if (savedInstanceState == null) {
            switchFragment(HomeFragment())
        }
        init()
    }

    private fun init() {
        binding.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false) // Title 제거

            bottomNavi.setOnItemSelectedListener { item ->
                val position = menuItems.indexOf(item.itemId)
                when (position) {
                    0 -> switchFragment(HomeFragment())
                    1 -> switchFragment(SearchFragment())
                    2 -> switchFragment(AddFragment())
                    3 -> switchFragment(ProfileFragment())
                    4 -> switchFragment(ChatFragment())
                    else -> return@setOnItemSelectedListener false
                }
                true
            }
        }
    }

    fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                startActivity(Intent(this@MainActivity, SettingActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}