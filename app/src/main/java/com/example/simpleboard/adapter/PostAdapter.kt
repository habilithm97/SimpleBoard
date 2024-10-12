package com.example.simpleboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleboard.R
import com.example.simpleboard.data.Post
import com.example.simpleboard.databinding.ItemHomePostBinding
import com.example.simpleboard.databinding.ItemProfilePostBinding

class PostAdapter(private val fragmentType: Int, private val onPostDelete: (String) -> Unit) :
    ListAdapter<Post, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
        const val HOME = 0
        const val PROFILE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return fragmentType
    }

    inner class HomePostViewHolder(private val binding: ItemHomePostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.post = post // XML에서 직접 데이터를 참조하여 자동으로 UI 업데이트
            binding.executePendingBindings() // 지연된 바인딩을 즉시 실행하여 데이터가 뷰에 반영되도록

            binding.optionsMenu.setOnClickListener { view ->
                showPopupMenu(view, post.id)
            }
        }

        private fun showPopupMenu(view: View, postId: String) {
            PopupMenu(view.context, view).apply {
                inflate(R.menu.post_options_menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.delete -> {
                            onPostDelete(postId)
                            true
                        }
                        else -> false
                    }
                }
                show()
            }
        }
    }

    inner class ProfilePostViewHolder(private val binding: ItemProfilePostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.post = post
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HOME -> { val binding = ItemHomePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomePostViewHolder(binding)
            }
            PROFILE -> {
                val binding = ItemProfilePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ProfilePostViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = getItem(position)
        when (holder) {
            is HomePostViewHolder -> holder.bind(post)
            is ProfilePostViewHolder -> holder.bind(post)
        }
    }
}