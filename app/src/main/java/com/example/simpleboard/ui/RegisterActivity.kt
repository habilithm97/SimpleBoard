package com.example.simpleboard.ui

import android.content.Context
import android.graphics.Color
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
    private lateinit var toast: Toast

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
            toolbar.navigationIcon?.setTint(Color.WHITE)

            btnRegister.setOnClickListener {
                val email = edtEmail.text.toString().trim()
                val pw = edtPw.text.toString().trim()

                if(email.isEmpty()) {
                    showToast(baseContext, "이메일을 입력해 주세요. ")
                    return@setOnClickListener
                }
                if(pw.isEmpty()) {
                    showToast(baseContext, "비밀번호를 입력해 주세요. ")
                    return@setOnClickListener
                }

                // 회원가입
                MyApplication.auth.createUserWithEmailAndPassword(email, pw) // 사용자 생성 요청
                    .addOnCompleteListener(this@RegisterActivity) { task ->
                        edtEmail.text.clear()
                        edtPw.text.clear()

                        if (task.isSuccessful) { // 회원가입 성공
                            // 생성된 사용자의 이메일로 인증 메일 전송 요청
                            MyApplication.auth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener { sendTask ->
                                    if (sendTask.isSuccessful) { // 인증 메일 전송 성공
                                        showToast(baseContext, "회원가입에 성공했습니다. 전송된 메일을 확인해 주세요. ")
                                        finish()
                                    } else { // 인증 메일 전송 실패
                                        showToast(baseContext, "메일 전송에 실패했습니다. ")
                                    }
                                }
                        } else { // 회원가입 실패
                            showToast(baseContext, "회원가입에 실패했습니다. ")
                        }
                    }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showToast(context: Context, msg: String) { // 토스트 메시지 중복 방지
        toast.cancel()
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast.show()
    }
}