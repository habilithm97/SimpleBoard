package com.example.simpleboard.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleboard.FirestoreRepository
import com.example.simpleboard.Post
import com.example.simpleboard.PostViewModel
import com.example.simpleboard.PostViewModelFactory
import com.example.simpleboard.R
import com.example.simpleboard.adapter.PostAdapter
import com.example.simpleboard.databinding.FragmentHomeBinding
import java.util.UUID

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

        val repository = FirestoreRepository()
        val viewModelFactory = PostViewModelFactory(repository)
        val postViewModel = ViewModelProvider(this@HomeFragment, viewModelFactory)[PostViewModel::class.java]

        binding.recyclerView.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        postViewModel.posts.observe(viewLifecycleOwner) { posts ->
            postAdapter.submitList(posts)
        }
        postViewModel.getPosts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 릭 방지
    }
}