package com.example.github.ui.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.github.base.BaseViewModel
import com.example.github.base.ModelItem
import com.example.github.domain.usecase.repo.GetReposUseCase
import com.example.github.domain.usecase.user.GetUserUseCase
import com.example.github.extension.tryWithSuspend
import com.example.github.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getReposUseCase: GetReposUseCase,
    private val userItemMapper: UserItemMapper,
    private val repoItemMapper: RepoItemMapper
) : BaseViewModel() {

    val user = MutableLiveData<UserItem>()
    val repos = MutableLiveData<List<RepoItem>>()
    private val currentPage = MutableLiveData<Int>()
    private val currentList = mutableListOf<ModelItem>()
    private var nextPage = -1

    val items = MediatorLiveData<List<ModelItem>>().apply {
        addSource(user) { userItem ->
            clearToRefresh()
            currentList.add(0, userItem)
            value = currentList.toList()
        }

        addSource(currentPage) { page ->
            if (page != null && page != FIRST_PAGE) {
                clearToRefresh()
                currentList.add(PageHeaderItem(page))
                value = currentList.toList()
            }
        }

        addSource(repos) { repoItems ->
            clearToRefresh()
            currentList.addAll(repoItems)
            value = currentList.toList()
        }
    }

    val isRefresh = MutableLiveData<Boolean>().apply { value = false }

    fun init() {
        getUser()
        getRepos()
    }

    fun refresh() {
        isRefresh.value = true
        nextPage = -1
        items.value = emptyList()
        init()
    }

    fun loadMore() {
        getRepos(page = nextPage)
    }

    fun getUser(id: String = USER_ID_DEFAULT) {
        viewModelScope.launch {
            try {
                user.value = userItemMapper.mapToPresentation(
                    getUserUseCase.createObservable(
                        GetUserUseCase.Params(id)
                    )
                )
            } catch (e: Exception) {
                isRefresh.value = false
            }
        }
    }

    fun getRepos(id: String = USER_ID_DEFAULT, page: Int = FIRST_PAGE) {
        if ((nextPage == -1 || isLoading.value == true) && page != FIRST_PAGE) {
            return
        }

        viewModelScope.launch {
            try {
                if (isRefresh.value == false) {
                    isLoading.value = true
                }
                val listRepo = tryWithSuspend {
                    getReposUseCase.createObservable(GetReposUseCase.Params(id, page))
                }
                isLoading.value = false
                if (listRepo.isNullOrEmpty()) {
                    nextPage = -1
                } else {
                    currentPage.value = page
                    nextPage = page + 1
                    repos.value = listRepo.map { repo -> repoItemMapper.mapToPresentation(repo) }
                }
            } catch (e: Exception) {
                isRefresh.value = false
                isLoading.value = false
            }
        }
    }

    private fun clearToRefresh() {
        if (isRefresh.value == true) {
            currentList.clear()
            isRefresh.value = false
        }
    }

    companion object {
        private const val USER_ID_DEFAULT = "google"
        private const val FIRST_PAGE = 1
    }
}
