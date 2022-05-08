package com.example.github.domain.model

data class User(
    val avatarUrl: String? = null,
    val blog: String? = null,
    val email: String? = null,
    val id: Int,
    val name: String? = null,
    val publicRepos: Int? = 0
) : Model()
