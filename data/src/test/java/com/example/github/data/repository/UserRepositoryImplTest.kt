package com.example.github.data.repository

import com.example.github.data.UserRepositoryImpl
import com.example.github.data.createRepoEntity
import com.example.github.data.createUserEntity
import com.example.github.data.model.RepoEntityMapper
import com.example.github.data.model.UserEntityMapper
import com.example.github.data.remote.api.GithubApi
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.mock

class UserRepositoryImplTest {
    private lateinit var userRepositoryImpl: UserRepositoryImpl

    private val githubApiMock = mock(GithubApi::class.java)
    private val userEntityMapper = UserEntityMapper()
    private val repoEntityMapper = RepoEntityMapper()

    @Before
    fun setup() {
        userRepositoryImpl = UserRepositoryImpl(githubApiMock, userEntityMapper, repoEntityMapper)
    }

    @Test
    fun getUserSuccess() {
        val id = anyString()
        val userResponse = createUserEntity()
        Mockito.`when`(githubApiMock.getUser(id)).thenReturn(Single.just(userResponse))

        val test = userRepositoryImpl.getUser(id).test()

        test.assertValue { results ->
            results == userResponse.let { userEntityMapper.mapToDomain(it) }
        }
        test.assertNoErrors()
        test.assertComplete()
        test.assertTerminated()
    }

    @Test
    fun getUserFail() {
        val error = RuntimeException()
        val id = anyString()
        Mockito.`when`(githubApiMock.getUser(id)).thenReturn(Single.error(error))

        val test = userRepositoryImpl.getUser(id).test()
        test.assertNoValues()
        test.assertError(error)
        test.assertNotComplete()
        test.assertTerminated()
    }

    @Test
    fun getReposSuccess() {
        val id = anyString()
        val page = anyInt()
        val perPage = anyInt()

        val reposResponse = listOf(createRepoEntity())
        Mockito.`when`(githubApiMock.getRepos(id, page, perPage))
            .thenReturn(Single.just(reposResponse))

        val test = userRepositoryImpl.getRepos(id, page).test()

        test.assertValue { results ->
            results == reposResponse.map { repo -> repoEntityMapper.mapToDomain(repo) }
        }
        test.assertNoErrors()
        test.assertComplete()
        test.assertTerminated()
    }

    @Test
    fun getReposFail() {
        val error = RuntimeException()
        val id = anyString()
        val page = anyInt()
        val perPage = anyInt()

        Mockito.`when`(githubApiMock.getRepos(id, page, perPage)).thenReturn(Single.error(error))

        val test = userRepositoryImpl.getRepos(id, page).test()
        test.assertNoValues()
        test.assertError(error)
        test.assertNotComplete()
        test.assertTerminated()
    }
}
