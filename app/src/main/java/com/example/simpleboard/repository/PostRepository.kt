package com.example.simpleboard.repository

import com.example.simpleboard.data.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

/* Firestore : Firebase의 클라우드 DB 서비스로, NoSQL 방식의 실시간 DB를 제공함
 데이터를 컬렉션과 문서로 구성하여 저장하며 서버리스로 작동해 백엔드 설정 없이도 실시간으로 DB를 다룰 수 있음 */
class PostRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val postCollection = firestore.collection("posts")

    suspend fun addPost(post: Post) {
        runCatching {
            postCollection.add(post).await() // await() : 작업이 완료될 때까지 대기
        }.onFailure { it.printStackTrace() }
    }

    fun getPostsSnapshotListener(onPostsUpdated: (List<Post>) -> Unit) {
        val query = postCollection.orderBy("createdAt")
        query.addSnapshotListener { snapshot, error ->
            // 가져온 Snapshot에서 문서들을 순회하며 Post 객체로 변환
            val posts = snapshot?.documents?.mapNotNull {
                it.toObject(Post::class.java)
            } ?: emptyList() // Snapshot이 null이면 빈 리스트 반환

            onPostsUpdated(posts) // 변환된 posts를 전달

            if (error != null) {
                error.printStackTrace()
                return@addSnapshotListener
            }
        }
    }
}