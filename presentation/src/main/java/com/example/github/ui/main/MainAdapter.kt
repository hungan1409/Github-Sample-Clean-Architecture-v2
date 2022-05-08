package com.example.github.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.github.R
import com.example.github.base.BaseRecyclerAdapter
import com.example.github.base.ModelItem
import com.example.github.databinding.ItemPageHeaderBinding
import com.example.github.databinding.ItemRepoBinding
import com.example.github.databinding.ItemUserBinding
import com.example.github.model.PageHeaderItem
import com.example.github.model.RepoItem
import com.example.github.model.UserItem

class MainAdapter(val onClickRepoListener: ((RepoItem) -> Unit)?) :
    BaseRecyclerAdapter<ModelItem>(DIFF_CALLBACK) {

    override fun createBinding(parent: ViewGroup, viewType: Int?): ViewDataBinding {
        if (viewType != null && viewType != -1) {
            when (viewType) {
                R.layout.item_repo -> {
                    val binding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        viewType,
                        parent,
                        false
                    ) as ItemRepoBinding
                    binding.apply {
                        root.setOnClickListener {
                            item?.let {
                                onClickRepoListener?.invoke(it)
                            }
                        }
                    }
                    return binding
                }
                else -> return DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    viewType,
                    parent,
                    false
                )
            }
        } else {
            throw Throwable("Not found this view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UserItem -> R.layout.item_user
            is PageHeaderItem -> R.layout.item_page_header
            is RepoItem -> R.layout.item_repo
            else -> -1
        }
    }

    override fun bind(binding: ViewDataBinding, item: ModelItem) {
        when (binding) {
            is ItemUserBinding -> {
                binding.item = item as UserItem
            }

            is ItemPageHeaderBinding -> {
                binding.item = item as PageHeaderItem
            }

            is ItemRepoBinding -> {
                binding.item = item as RepoItem
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ModelItem>() {
            override fun areItemsTheSame(oldItem: ModelItem, newItem: ModelItem): Boolean {
                return if (oldItem is UserItem && newItem is UserItem) {
                    oldItem.id == newItem.id
                } else if (oldItem is PageHeaderItem && newItem is PageHeaderItem) {
                    oldItem.page == newItem.page
                } else if (oldItem is RepoItem && newItem is RepoItem) {
                    oldItem.id == newItem.id
                } else {
                    false
                }
            }

            override fun areContentsTheSame(oldItem: ModelItem, newItem: ModelItem): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }
}
