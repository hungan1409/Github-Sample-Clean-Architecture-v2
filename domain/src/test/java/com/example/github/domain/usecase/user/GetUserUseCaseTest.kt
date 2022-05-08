package com.example.github.domain.usecase.user

import com.example.github.domain.Constants
import com.example.github.domain.createUser
import com.example.github.domain.repository.UserRepository
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*

class GetUserUseCaseTest {
    private lateinit var getUserUseCase: GetUserUseCase

    private val userRepository = mock(UserRepository::class.java)

    @Before
    fun setup() {
        getUserUseCase = GetUserUseCase(userRepository)
    }

    @After
    fun clear() {
        getUserUseCase.onCleared()
    }

    @Test
    fun createObservable() {
        val params = GetUserUseCase.Params(id = anyString())
        getUserUseCase.createObservable(params)

        verify(userRepository).getUser(params.id)
    }

    @Test
    fun getUserParamsNull() {
        val test = getUserUseCase.createObservable(null).test()
        test.assertError {
            it.message == Constants.PARAMS_ERROR_MSG
        }
    }

    @Test
    fun getUserComplete() {
        given(
            userRepository.getUser(anyString())
        ).willReturn(Single.just(createUser()))

        val test = getUserUseCase.createObservable(GetUserUseCase.Params(anyString())).test()
        test.assertComplete()
    }

    @Test
    fun getUserReturnData() {
        val params = GetUserUseCase.Params(id = anyString())
        val user = createUser()

        `when`(
            userRepository.getUser(id = params.id)
        ).thenReturn(Single.just(user))
        val test = getUserUseCase.createObservable(params).test()
        test.assertValue(user)
    }

    @Test
    fun getUserFail() {
        val error = Throwable(Constants.PARAMS_ERROR_MSG)
        given(
            userRepository.getUser(anyString())
        ).willReturn(Single.error(error))

        val test = getUserUseCase.createObservable(GetUserUseCase.Params(id = anyString())).test()

        test.assertError { it.message == Constants.PARAMS_ERROR_MSG }
        test.assertNoValues()
        test.assertNotComplete()
        test.assertTerminated()
    }
}