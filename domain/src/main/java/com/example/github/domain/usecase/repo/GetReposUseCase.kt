package com.example.github.domain.usecase.repo

import com.example.github.domain.Constants
import com.example.github.domain.model.Repo
import com.example.github.domain.repository.UserRepository
import com.example.github.domain.usecase.UseCase
import javax.inject.Inject

open class GetReposUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<GetReposUseCase.Params, List<Repo>>() {

    override suspend fun createObservable(params: Params?): List<Repo> {
        return when (params) {
            null -> error { Throwable(Constants.PARAMS_ERROR_MSG) }
            else -> userRepository.getRepos(params.id, params.page)
        }
    }

    data class Params(val id: String, val page: Int)
}
