package com.example.simpleboard.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleboard.adapter.PostAdapter
import com.example.simpleboard.databinding.FragmentHomeBinding
import com.example.simpleboard.repository.PostRepository
import com.example.simpleboard.viewmodel.PostViewModel
import com.example.simpleboard.viewmodel.PostViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val postAdapter by lazy { PostAdapter() }

    private val postViewModel: PostViewModel by lazy {
        val repository = PostRepository()
        val viewModelFactory = PostViewModelFactory(repository)
        ViewModelProvider(this@HomeFragment, viewModelFactory)[PostViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        // LiveData 관찰 및 데이터 업데이트
        postViewModel.apply {
            posts.observe(viewLifecycleOwner) { postAdapter.submitList(it) }
            getPosts()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 릭 방지
    }
}