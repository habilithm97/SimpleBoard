package com.example.simpleboard.application

import androidx.multidex.MultiDexApplication
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

/** 앱 전역에서 Firebase 인증 객체를 사용할 수 있는 Application 클래스
    -> Firebase 인증 객체 설정, 사용자의 로그인 및 이메일 인증 상태를 관리 **/
class MyApplication: MultiDexApplication() {
    companion object {
        lateinit var auth: FirebaseAuth // Firebase 인증 객체
        var email: String? = null

        // 인증 상태 확인
        fun checkAuth(): Boolean {
            val currentUser = auth.currentUser

            // 로그인 상태
            return currentUser?.let { // 로그인 o
                email = currentUser.email
                currentUser.isEmailVerified // 이메일 인증 여부
            } ?: let { // 로그인 x
                false
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        auth = Firebase.auth
    }
}