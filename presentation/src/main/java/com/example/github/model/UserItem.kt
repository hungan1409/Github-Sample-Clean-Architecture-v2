package com.example.github.model

import com.example.github.base.ItemMapper
import com.example.github.base.ModelItem
import com.example.github.domain.model.User
import javax.inject.Inject

data class UserItem(
    val avatarUrl: String? = null,
    val blog: String? = null,
    val email: String? = null,
    val id: Int,
    val name: String? = null,
    val publicRepos: Int? = 0
) : ModelItem()

open class UserItemMapper @Inject constructor() : ItemMapper<User, UserItem> {
    override fun mapToPresentation(model: User): UserItem = UserItem(
        avatarUrl = model.avatarUrl,
        blog = model.blog,
        email = model.email,
        id = model.id,
        name = model.name,
        publicRepos = model.publicRepos
    )

    override fun mapToDomain(modelItem: UserItem) = User(
        avatarUrl = modelItem.avatarUrl,
        blog = modelItem.blog,
        email = modelItem.email,
        id = modelItem.id,
        name = modelItem.name,
        publicRepos = modelItem.publicRepos
    )
}
