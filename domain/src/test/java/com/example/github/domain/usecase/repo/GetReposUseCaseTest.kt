package com.example.github.domain.usecase.repo

import com.example.github.domain.Constants
import com.example.github.domain.createRepo
import com.example.github.domain.repository.UserRepository
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*

class GetReposUseCaseTest {
    private lateinit var getReposUseCase: GetReposUseCase

    private val userRepository = mock(UserRepository::class.java)

    @Before
    fun setup() {
        getReposUseCase = GetReposUseCase(userRepository)
    }

    @After
    fun clear() {
        getReposUseCase.onCleared()
    }

    @Test
    fun createObservable() {
        val params = GetReposUseCase.Params(id = anyString(), page = anyInt())
        getReposUseCase.createObservable(params)

        verify(userRepository).getRepos(params.id, params.page)
    }

    @Test
    fun getReposParamsNull() {
        val test = getReposUseCase.createObservable(null).test()
        test.assertError {
            it.message == Constants.PARAMS_ERROR_MSG
        }
    }

    @Test
    fun getReposComplete() {
        given(
            userRepository.getRepos(anyString(), anyInt())
        ).willReturn(Single.just(listOf(createRepo())))

        val test =
            getReposUseCase.createObservable(GetReposUseCase.Params(anyString(), anyInt())).test()
        test.assertComplete()
    }

    @Test
    fun getRepoReturnData() {
        val params = GetReposUseCase.Params(id = anyString(), page = anyInt())
        val repos = listOf(createRepo())

        `when`(
            userRepository.getRepos(id = params.id, page = params.page)
        ).thenReturn(Single.just(repos))
        val test = getReposUseCase.createObservable(params).test()
        test.assertValue(repos)
    }

    @Test
    fun getRepoFail() {
        val error = Throwable(Constants.PARAMS_ERROR_MSG)
        given(
            userRepository.getRepos(anyString(), anyInt())
        ).willReturn(Single.error(error))

        val test = getReposUseCase.createObservable(
            GetReposUseCase.Params(
                id = anyString(),
                page = anyInt()
            )
        ).test()

        test.assertError { it.message == Constants.PARAMS_ERROR_MSG }
        test.assertNoValues()
        test.assertNotComplete()
        test.assertTerminated()
    }
}