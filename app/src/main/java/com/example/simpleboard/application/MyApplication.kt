package com.example.simpleboard.application

import androidx.multidex.MultiDexApplication
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

/* 멀티덱스 -> 많은 라이브러리를 사용하는 경우에 앱이 정상적으로 작동될 수 있음
(Firebase, Google Services 등) */
class MyApplication : MultiDexApplication() {
    companion object {
        lateinit var auth: FirebaseAuth // Firebase 인증 객체
        var email: String? = null

        // 인증 상태 확인
        fun checkAuth(): Boolean {
            return auth.currentUser?.let { // 로그인 o
                email = it.email
                it.isEmailVerified // 이메일 인증 여부
            } ?: false // 로그인 x
        }
    }

    override fun onCreate() {
        super.onCreate()

        auth = Firebase.auth
    }
}