package com.example.github.domain.usecase.user

import com.example.github.domain.Constants
import com.example.github.domain.model.User
import com.example.github.domain.repository.UserRepository
import com.example.github.domain.usecase.UseCase
import io.reactivex.Single
import javax.inject.Inject

open class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<GetUserUseCase.Params, Single<User>>() {

    override fun createObservable(params: Params?): Single<User> {
        return when (params) {
            null -> Single.error { Throwable(Constants.PARAMS_ERROR_MSG) }
            else -> userRepository.getUser(params.id)
        }
    }

    data class Params(val id: String)
}
