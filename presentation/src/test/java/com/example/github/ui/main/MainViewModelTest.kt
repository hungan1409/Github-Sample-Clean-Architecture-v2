package com.example.github.ui.main

import androidx.lifecycle.Observer
import com.example.github.*
import com.example.github.domain.usecase.repo.GetReposUseCase
import com.example.github.domain.usecase.user.GetUserUseCase
import com.example.github.model.RepoItem
import com.example.github.model.RepoItemMapper
import com.example.github.model.UserItemMapper
import com.example.github.ui.BaseViewModelTest
import io.reactivex.Single
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals

class MainViewModelTest : BaseViewModelTest() {
    @Mock
    private lateinit var getUserUseCase: GetUserUseCase

    @Mock
    private lateinit var getReposUseCase: GetReposUseCase

    @Mock
    private var userItemMapper = UserItemMapper()

    @Mock
    private var repoItemMapper = RepoItemMapper()

    private lateinit var viewModel: MainViewModel

    override fun setup() {
        super.setup()
        viewModel = MainViewModel(
            getUserUseCase,
            getReposUseCase,
            userItemMapper,
            repoItemMapper
        )
    }

    @Test
    fun getUserSuccess() {
        val user = createUser()
        val userItem = createUserItem()

        `when`(getUserUseCase.createObservable(GetUserUseCase.Params(Matchers.anyString()))).thenReturn(
            Single.just(user)
        )
        `when`(userItemMapper.mapToPresentation(user)).thenReturn(userItem)

        viewModel.getUser(Matchers.anyString())
        assertEquals(viewModel.user.value, userItem)
    }

    @Test
    fun getUserFail() {
        val error = RuntimeException()
        val user = createUser()
        val userItem = createUserItem()

        `when`(getUserUseCase.createObservable(GetUserUseCase.Params(Matchers.anyString()))).thenReturn(
            Single.error(error)
        )
        `when`(userItemMapper.mapToPresentation(user)).thenReturn(userItem)

        viewModel.getUser(Matchers.anyString())
        assertEquals(viewModel.user.value, null)
    }

    @Test
    fun getReposSuccess() {
        val repo = createRepo()
        val repoItem = createRepoItem()
        val id = "google"
        val page = 1

        `when`(
            getReposUseCase.createObservable(GetReposUseCase.Params(id = id, page = page))
        ).thenReturn(
            Single.just(
                listOf(repo)
            )
        )
        `when`(repoItemMapper.mapToPresentation(repo)).thenReturn(repoItem)

        val observer = mock<Observer<List<RepoItem>>>()
        viewModel.repos.observeForever(observer)
        viewModel.getRepos(id, page)

        assertEquals(viewModel.repos.value?.get(0), repoItem)
    }

    @Test
    fun getReposFail() {
        val error = RuntimeException()
        val id = "google"
        val page = 1

        `when`(
            getReposUseCase.createObservable(GetReposUseCase.Params(id = id, page = page))
        ).thenReturn(
            Single.error(error)
        )

        val observer = mock<Observer<List<RepoItem>>>()
        viewModel.repos.observeForever(observer)
        viewModel.getRepos(id, page)

        assertEquals(viewModel.repos.value, null)
    }
}
