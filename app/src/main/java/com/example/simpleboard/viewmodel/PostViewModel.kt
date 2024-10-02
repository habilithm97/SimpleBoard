package com.example.simpleboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleboard.data.Post
import com.example.simpleboard.repository.PostRepository
import kotlinx.coroutines.launch

// ViewModel : Repository에서 데이터를 가져와 UI에 필요한 데이터를 관리
class PostViewModel(private val repository: PostRepository) : ViewModel() {

    /* LiveData를 사용하여 데이터의 변경을 관찰하고 UI에 업데이트
    -MutableLiveData -> 내부에서 값 변경 가능
    -LiveData -> 외부에서 읽기만 가능
    -> 캡슐화 : 외부에서 직접 변경할 수 없도록 LiveData로 감싸기 때문에
     데이터의 일관성을 유지할 수 있음 */

    private val _addPostStatus = MutableLiveData<Boolean>() // Post 추가 작업 상태
    val addPostStatus: LiveData<Boolean> get() = _addPostStatus

    private val _posts = MutableLiveData<List<Post>>() // Firestore에서 가져온 Post 리스트
    val posts: LiveData<List<Post>> get() = _posts

    fun addPost(post: Post) = viewModelScope.launch {
        _addPostStatus.value = runCatching { repository.addPost(post) }.isSuccess
    }

    fun getPosts() = viewModelScope.launch {
        _posts.value = repository.getPosts()
    }
}