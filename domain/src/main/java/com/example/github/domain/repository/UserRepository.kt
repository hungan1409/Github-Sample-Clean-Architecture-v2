package com.example.github.domain.repository

import com.example.github.domain.model.Repo
import com.example.github.domain.model.User

interface UserRepository : Repository {

    suspend fun getUser(id: String): User

    suspend fun getRepos(id: String, page: Int): List<Repo>
}
