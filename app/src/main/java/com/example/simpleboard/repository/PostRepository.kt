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

    suspend fun getPosts(): List<Post> {
        return runCatching {
            /* Firestore에서 모든 문서들을 비동기로 가져오고 완료될 때까지 대기,
             가져온 문서들을 순회하며 각 문서를 Post 객체로 변환 (변환된 값이 null인 경우는 제외) */
            postCollection
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Post::class.java) }
        }.getOrElse {
            it.printStackTrace()
            emptyList()
        }
    }
}