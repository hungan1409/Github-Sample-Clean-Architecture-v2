package com.example.github.data

import com.example.github.data.model.RepoEntityMapper
import com.example.github.data.model.UserEntityMapper
import com.example.github.data.remote.api.GithubApi
import com.example.github.domain.model.Repo
import com.example.github.domain.model.User
import com.example.github.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi,
    private val userEntityMapper: UserEntityMapper,
    private val repoEntityMapper: RepoEntityMapper
) : UserRepository {

    override fun getUser(id: String): Single<User> {
        return githubApi.getUser(id).map { userEntityMapper.mapToDomain(it) }
    }

    override fun getRepos(id: String, page: Int): Single<List<Repo>> {
        return githubApi.getRepos(id, page).map {
            it.map { entity -> repoEntityMapper.mapToDomain(entity) }
        }
    }
}
