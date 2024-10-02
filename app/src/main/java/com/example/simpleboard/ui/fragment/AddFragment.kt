package com.example.simpleboard.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.simpleboard.R
import com.example.simpleboard.data.Post
import com.example.simpleboard.databinding.FragmentAddBinding
import com.example.simpleboard.repository.PostRepository
import com.example.simpleboard.ui.activity.MainActivity
import com.example.simpleboard.viewmodel.PostViewModel
import com.example.simpleboard.viewmodel.PostViewModelFactory
import java.util.UUID

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
                    val post = Post(id = UUID.randomUUID().toString(), content)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 릭 방지
    }
}