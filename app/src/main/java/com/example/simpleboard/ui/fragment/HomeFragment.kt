package com.example.simpleboard.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleboard.Post
import com.example.simpleboard.adapter.PostAdapter
import com.example.simpleboard.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val postAdapter by lazy { PostAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            recyclerView.apply {
                adapter = postAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        val samplePosts = listOf(
            Post(id = "1", title = "첫 번째 게시물", content = "첫 번째 게시물입니다. "),
            Post(id = "2", title = "두 번째 게시물", content = "두 번째 게시물입니다. "),
            Post(id = "1", title = "첫 번째 게시물", content = "첫 번째 게시물입니다. "),
            Post(id = "2", title = "두 번째 게시물", content = "두 번째 게시물입니다. ")
        )
        postAdapter.submitList(samplePosts)
    }
}