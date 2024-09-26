package com.example.simpleboard

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val postCollection = firestore.collection("posts")

    suspend fun addPost(post: Post) {
        try {
            postCollection.add(post).await() // await() : 작업이 완료될 때까지 기다리는 코루틴 함수
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*
    suspend fun getPosts(): List<Post> {
        return try {
            val result = postCollection.get().await()
            // 문서들을 순회하며 각 문서를 Post 객체로 변환
            result.documents.map { document ->
                document.toObject(Post::class.java)!!
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    } */

    suspend fun getPosts(): List<Post> {
        return try {
            val result = postCollection.get().await()
            val posts = result.documents.map { document ->
                document.toObject(Post::class.java)!!
            }
            posts
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
/**
 * Firestore : 데이터 문서(Document)와 컬렉션(Collection) 단위로 관리하는 NoSQL 데이터베이스
  -> postCollection : post 데이터를 저장할 위치를 나타냄
 */