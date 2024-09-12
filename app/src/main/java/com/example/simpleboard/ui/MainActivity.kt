package com.example.simpleboard.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.simpleboard.R
import com.example.simpleboard.adapter.ViewPagerAdapter
import com.example.simpleboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

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
            viewPager.adapter = ViewPagerAdapter(this@MainActivity)

            val menuItems = listOf(
                R.id.nav_home, R.id.nav_search,
                R.id.nav_add, R.id.nav_profile, R.id.nav_chat
            )
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position in menuItems.indices) {
                        bottomNavi.selectedItemId = menuItems[position]
                    }
                }
            })
            bottomNavi.setOnItemSelectedListener { item ->
                val index = menuItems.indexOf(item.itemId)
                if (index != -1) {
                    viewPager.currentItem = index
                }
                true
            }
        }
    }
}