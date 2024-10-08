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
            layoutManager = LinearLayoutManager(requireContext()).apply {
                reverseLayout = true
                stackFromEnd = true
            }
        }
        // LiveData 관찰
        postViewModel.posts.observe(viewLifecycleOwner) { posts ->
            postAdapter.submitList(posts)
        }
    }

    override fun onResume() {
        super.onResume()

        val itemCount = postAdapter.itemCount
        if (itemCount > 0) {
            binding.recyclerView.smoothScrollToPosition(itemCount - 1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 릭 방지
    }
}