package com.example.github.data.model

import com.example.github.data.base.EntityMapper
import com.example.github.data.base.ModelEntity
import com.example.github.domain.model.User
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class UserEntity(
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @SerializedName("blog")
    val blog: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("public_repos")
    val publicRepos: Int? = 0
) : ModelEntity()

class UserEntityMapper @Inject constructor() : EntityMapper<User, UserEntity> {
    override fun mapToDomain(entity: UserEntity) = User(
        avatarUrl = entity.avatarUrl,
        blog = entity.blog,
        email = entity.email,
        id = entity.id,
        name = entity.name,
        publicRepos = entity.publicRepos
    )

    override fun mapToEntity(model: User) = UserEntity(
        avatarUrl = model.avatarUrl,
        blog = model.blog,
        email = model.email,
        id = model.id,
        name = model.name,
        publicRepos = model.publicRepos
    )
}
