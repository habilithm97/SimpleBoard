package com.example.simpleboard

data class Post(
    val id: String = "", // Firestore 문서 id
    val content: String = ""
)