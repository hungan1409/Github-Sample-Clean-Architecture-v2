package com.example.github.model

import com.example.github.base.ItemMapper
import com.example.github.base.ModelItem
import com.example.github.domain.model.Repo
import com.example.github.util.convertDateToDay
import javax.inject.Inject

data class RepoItem(
    val description: String? = null,
    val forksCount: Int? = 0,
    val id: Int,
    val language: String? = null,
    val fullName: String? = null,
    val stargazersCount: Int? = 0,
    val updatedAt: String? = null,
    val watchersCount: Int? = 0,
    val htmlUrl: String? = null
) : ModelItem() {
    fun convertUpdateAtToDayAgo(): String? {
        return if (updatedAt.isNullOrBlank()) {
            ""
        } else {
            convertDateToDay(updatedAt)
        }
    }
}


open class RepoItemMapper @Inject constructor() : ItemMapper<Repo, RepoItem> {
    override fun mapToPresentation(model: Repo) = RepoItem(
        description = model.description,
        forksCount = model.forksCount,
        fullName = model.fullName,
        id = model.id,
        language = model.language,
        stargazersCount = model.stargazersCount,
        updatedAt = model.updatedAt,
        watchersCount = model.watchersCount,
        htmlUrl = model.htmlUrl
    )

    override fun mapToDomain(modelItem: RepoItem) = Repo(
        description = modelItem.description,
        forksCount = modelItem.forksCount,
        fullName = modelItem.fullName,
        id = modelItem.id,
        language = modelItem.language,
        stargazersCount = modelItem.stargazersCount,
        updatedAt = modelItem.updatedAt,
        watchersCount = modelItem.watchersCount,
        htmlUrl = modelItem.htmlUrl
    )
}
