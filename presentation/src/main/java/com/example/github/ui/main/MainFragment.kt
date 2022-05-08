package com.example.github.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github.R
import com.example.github.base.BaseFragment
import com.example.github.databinding.FragmentMainBinding
import com.example.github.model.RepoItem
import com.example.github.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_main

    override val viewModel: MainViewModel by viewModels()

    private var mainAdapter by autoCleared<MainAdapter>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAfterTransition()
                }
            })
        subscribeUI()
        viewModel.init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startShimmer()
        mainAdapter = MainAdapter { repoItem -> onClickRepo(repoItem) }
        with(viewDataBinding) {
            recycler.adapter = mainAdapter
            recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastPosition == mainAdapter.itemCount - REMAINING_ITEM_REFRESH) {
                        viewModel?.loadMore()
                    }
                }
            })

            swipeRefresh.setOnRefreshListener {
                viewModel?.refresh()
                startShimmer()
            }
        }
    }

    private fun onClickRepo(repoItem: RepoItem) {
        if (repoItem.htmlUrl.isNullOrEmpty().not()) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(repoItem.htmlUrl ?: ""))
            startActivity(browserIntent)
        }
    }

    private fun subscribeUI() = with(viewModel) {
        items.observe(viewLifecycleOwner) {
            stopShimmer()
            mainAdapter.submitList(it)
        }
    }

    private fun startShimmer() {
        viewDataBinding.apply {
            shimmerLayout.startShimmer()
            shimmerLayout.visibility = View.VISIBLE
        }
    }

    private fun stopShimmer() {
        viewDataBinding.apply {
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
        }
    }

    companion object {
        private const val REMAINING_ITEM_REFRESH = 5
    }
}
