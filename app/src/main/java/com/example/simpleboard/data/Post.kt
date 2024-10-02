package com.example.simpleboard.data

/* Firestore : 데이터를 가져올 때 객체를 역직렬화
 -> 누락된 매개변수로 인해 발생할 수 있는 문제를 방지하기 위한 기본값 설정
 -> 항상 유효한 상태의 객체 */

data class Post(
    val id: String = "",
    val content: String = ""
)