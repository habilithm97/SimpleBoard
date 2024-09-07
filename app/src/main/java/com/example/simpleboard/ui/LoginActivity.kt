package com.example.simpleboard.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simpleboard.application.MyApplication
import com.example.simpleboard.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

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
            btnSignup.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString().trim()
                val pw = edtPw.text.toString().trim()

                if(email.isEmpty()) {
                    Toast.makeText(baseContext, "이메일을 입력해 주세요. ", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(pw.isEmpty()) {
                    Toast.makeText(baseContext, "비밀번호를 입력해 주세요. ", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // 로그인
                MyApplication.auth.signInWithEmailAndPassword(email, pw)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        edtEmail.text.clear()
                        edtPw.text.clear()

                        if(task.isSuccessful) { // 로그인 성공
                            if(MyApplication.checkAuth()) { // 이메일 인증 성공
                                MyApplication.email = email
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                startActivity(intent)
                            } else { // 이메일 인증 실패
                                Toast.makeText(baseContext, "전송된 메일로 이메일 인증이 되지 않았습니다. ", Toast.LENGTH_SHORT).show()
                            }
                        } else { // 로그인 실패
                            Toast.makeText(baseContext, "로그인에 실패했습니다. ", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}