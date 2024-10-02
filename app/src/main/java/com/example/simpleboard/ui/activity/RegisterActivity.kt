package com.example.simpleboard.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simpleboard.application.MyApplication
import com.example.simpleboard.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private var toast: Toast? = null

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
            supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 활성화
            supportActionBar?.setDisplayShowTitleEnabled(false) // Title 제거

            edtEmail.requestFocus()

            btnRegister.setOnClickListener {
                val email = edtEmail.text.toString().trim()
                val pw = edtPw.text.toString().trim()

                if (email.isBlank() || pw.isBlank()) {
                    showToast(baseContext, "이메일과 비밀번호를 모두 입력해 주세요.")
                } else {
                    register(email, pw)
                }
            }
        }
    }

    private fun register(email: String, pw: String) {
        MyApplication.auth.createUserWithEmailAndPassword(email, pw)
            .addOnCompleteListener(this@RegisterActivity) { task ->
                binding.apply {
                    edtEmail.text.clear()
                    edtPw.text.clear()
                }

                if (task.isSuccessful) { // 회원가입 성공
                    // 생성된 사용자의 이메일로 인증 메일 전송 요청
                    MyApplication.auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener { sendTask ->
                            val msg = if (sendTask.isSuccessful) {
                                "회원가입에 성공했습니다. 전송된 메일을 확인해 주세요."
                            } else {
                                "메일 전송에 실패했습니다. "
                            }
                            showToast(baseContext, msg)
                            if (sendTask.isSuccessful) finish()
                        }
                } else { // 회원가입 실패
                    showToast(baseContext, "회원가입에 실패했습니다. ")
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun showToast(context: Context, msg: String) {
        toast?.cancel()
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT).apply { show() }
    }
}