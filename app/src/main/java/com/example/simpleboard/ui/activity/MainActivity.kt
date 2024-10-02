package com.example.simpleboard.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.simpleboard.R
import com.example.simpleboard.adapter.ViewPagerAdapter
import com.example.simpleboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val menuItems = listOf(
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
        init()
    }

    private fun init() {
        binding.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false) // Title 제거

            viewPager.adapter = ViewPagerAdapter(this@MainActivity)

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    bottomNavi.selectedItemId = menuItems.getOrNull(position) ?: return
                }
            })
            bottomNavi.setOnItemSelectedListener { item ->
                val position = menuItems.indexOf(item.itemId)
                if (position != -1) {
                    viewPager.currentItem = position
                } else {
                    return@setOnItemSelectedListener true
                }
                true
            }
        }
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