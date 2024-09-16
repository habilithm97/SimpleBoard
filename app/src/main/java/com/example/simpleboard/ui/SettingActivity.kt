package com.example.simpleboard.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
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
        val items = arrayOf(getString(R.string.logout))
        val adapter = ArrayAdapter(this@SettingActivity, android.R.layout.simple_list_item_1, items)

        binding.apply {
            listView.adapter = adapter
            listView.setOnItemClickListener { _, _, position, _ ->
                val selectedItem = items[position]

                if (selectedItem == getString(R.string.logout)) {
                    showLogoutDialog()
                }
            }
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this@SettingActivity).apply {
            setTitle(R.string.logout)
            setMessage("정말 로그아웃 하시겠습니까?")
            setPositiveButton(R.string.logout) { _: DialogInterface, _: Int ->
                logout()
            }
            setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
        }.create().show()
    }

    private fun logout() {
        MyApplication.auth.signOut()
        MyApplication.email = null

        val intent = Intent(this@SettingActivity, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish() // SettingActivity를 명시적으로 종료
    }
}