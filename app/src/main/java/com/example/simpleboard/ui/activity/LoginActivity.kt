package com.example.simpleboard.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simpleboard.application.MyApplication
import com.example.simpleboard.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
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
            btnSignup.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
            /*
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString().trim()
                val pw = edtPw.text.toString().trim()

                if (email.isBlank() || pw.isBlank()) {
                    showToast(baseContext, "이메일과 비밀번호를 모두 입력해 주세요.")
                } else {
                    login(email, pw)
                    progressBar.visibility = View.VISIBLE
                }
            } */
            btnLogin.setOnClickListener { // 테스트용
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        }
    }

    private fun login(email: String, pw: String) {
        MyApplication.auth.signInWithEmailAndPassword(email, pw)
            .addOnCompleteListener(this@LoginActivity) { task ->
                binding.edtEmail.text.clear()
                binding.edtPw.text.clear()

                if (task.isSuccessful) { // 로그인 성공
                    if (MyApplication.checkAuth()) { // 이메일 인증 성공
                        MyApplication.email = email
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    } else { // 이메일 인증 실패
                        showToast(baseContext, "전송된 메일로 이메일 인증이 되지 않았습니다. ")
                    }
                } else { // 로그인 실패
                    showToast(baseContext, "로그인에 실패했습니다. ")
                }
            }
    }

    private fun showToast(context: Context, msg: String) {
        toast?.cancel()
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT).apply { show() }
    }
}