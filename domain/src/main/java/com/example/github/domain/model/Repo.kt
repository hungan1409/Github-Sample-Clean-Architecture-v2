package com.example.github.domain.model

data class Repo(
    val description: String? = null,
    val forksCount: Int? = 0,
    val id: Int,
    val language: String? = null,
    val fullName: String? = null,
    val stargazersCount: Int? = 0,
    val updatedAt: String? = null,
    val watchersCount: Int? = 0,
    val htmlUrl: String? = null
) : Model()
