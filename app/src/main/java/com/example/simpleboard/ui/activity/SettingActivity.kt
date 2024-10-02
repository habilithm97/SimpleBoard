package com.example.simpleboard.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simpleboard.R
import com.example.simpleboard.application.MyApplication
import com.example.simpleboard.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    private fun init() {
        binding.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 활성화
                setDisplayShowTitleEnabled(false) // Title 제거
            }
            listView.apply {
                adapter = ArrayAdapter(
                    this@SettingActivity,
                    android.R.layout.simple_list_item_1,
                    arrayOf(getString(R.string.logout))
                )
                setOnItemClickListener { _, _, position, _ ->
                    if (position == 0) showLogoutDialog()
                }
            }
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this@SettingActivity).apply {
            setTitle(R.string.logout)
            setMessage("정말 로그아웃 하시겠습니까?")
            setPositiveButton(R.string.logout) { _, _ -> logout() }
            setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
        }.create().show()
    }

    private fun logout() {
        MyApplication.auth.signOut()
        MyApplication.email = null

        Intent(this@SettingActivity, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }.also { startActivity(it) }
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}