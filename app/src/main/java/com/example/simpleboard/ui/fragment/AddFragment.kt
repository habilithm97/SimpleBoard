package com.example.simpleboard.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.simpleboard.R
import com.example.simpleboard.data.Post
import com.example.simpleboard.databinding.FragmentAddBinding
import com.example.simpleboard.repository.PostRepository
import com.example.simpleboard.ui.activity.MainActivity
import com.example.simpleboard.viewmodel.PostViewModel
import com.example.simpleboard.viewmodel.PostViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private var toast: Toast? = null

    private val postViewModel: PostViewModel by lazy {
        val repository = PostRepository()
        val viewModelFactory = PostViewModelFactory(repository)
        ViewModelProvider(this@AddFragment, viewModelFactory)[PostViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnUpload.setOnClickListener {
                val content = edtContent.text.toString()
                if (content.isBlank()) {
                    showToast(requireContext(), R.string.content.toString())
                } else {
                    // Firestore에서 자동 생성된 문서 id 사용
                    val docRef = FirebaseFirestore.getInstance().collection("posts").document()
                    val postId = docRef.id // 자동 생성된 id

                    val post = Post(id = postId, content = content, createdAt = System.currentTimeMillis())

                    postViewModel.addPost(post)
                    edtContent.text.clear()
                    (activity as MainActivity).binding.viewPager.currentItem = 0
                }
            }
        }
    }

    private fun showToast(context: Context, msg: String) {
        toast?.cancel()
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT).apply { show() }
    }

    override fun onResume() {
        super.onResume()

        binding.edtContent.requestFocus()

        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.edtContent, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 릭 방지
    }
}