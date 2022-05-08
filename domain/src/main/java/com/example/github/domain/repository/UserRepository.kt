package com.example.github.domain.repository

import com.example.github.domain.model.Repo
import com.example.github.domain.model.User
import io.reactivex.Single

interface UserRepository : Repository {

    fun getUser(id: String): Single<User>

    fun getRepos(id: String, page: Int): Single<List<Repo>>
}
