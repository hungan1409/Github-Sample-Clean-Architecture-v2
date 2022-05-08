package com.example.github.data

import com.example.github.data.model.RepoEntityMapper
import com.example.github.data.model.UserEntityMapper
import com.example.github.data.remote.api.GithubApi
import com.example.github.domain.model.Repo
import com.example.github.domain.model.User
import com.example.github.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi,
    private val userEntityMapper: UserEntityMapper,
    private val repoEntityMapper: RepoEntityMapper
) : UserRepository {

    override suspend fun getUser(id: String): User {
        return userEntityMapper.mapToDomain(githubApi.getUser(id))
    }

    override suspend fun getRepos(id: String, page: Int): List<Repo> {
        return githubApi.getRepos(id, page).map { entity ->
            repoEntityMapper.mapToDomain(entity)
        }
    }
}
