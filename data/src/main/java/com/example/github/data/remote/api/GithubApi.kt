package com.example.github.data.remote.api

import com.example.github.data.model.RepoEntity
import com.example.github.data.model.UserEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("users/{id}")
    fun getUser(@Path("id") userId: String): Single<UserEntity>

    @GET("users/{id}/repos")
    fun getRepos(
        @Path("id") userId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PER_PAGE_DEFAULT
    ): Single<List<RepoEntity>>

    companion object {
        private const val PER_PAGE_DEFAULT = 25
    }
}
