package com.example.github.model

import com.example.github.createRepo
import com.example.github.createRepoItem
import org.junit.Before
import org.junit.Test

class RepoItemMapperTest {
    private lateinit var repoItemMapper: RepoItemMapper

    @Before
    fun setup() {
        repoItemMapper = RepoItemMapper()
    }

    @Test
    fun mapPresentationToDomainTest() {
        // generate user
        val item = createRepoItem()

        // mapper to domain
        val model = repoItemMapper.mapToDomain(item)

        assert(model.id == item.id)
        assert(model.fullName == item.fullName)
        assert(model.description == item.description)
        assert(model.language == item.language)
        assert(model.htmlUrl == item.htmlUrl)
        assert(model.forksCount == item.forksCount)
        assert(model.stargazersCount == item.stargazersCount)
        assert(model.watchersCount == item.watchersCount)
        assert(model.updatedAt == item.updatedAt)
    }

    @Test
    fun mapDomainToPresentationTest() {
        // generate entity model
        val model = createRepo()

        // mapper to presentation
        val item = repoItemMapper.mapToPresentation(model)

        assert(model.id == item.id)
        assert(model.fullName == item.fullName)
        assert(model.description == item.description)
        assert(model.language == item.language)
        assert(model.htmlUrl == item.htmlUrl)
        assert(model.forksCount == item.forksCount)
        assert(model.stargazersCount == item.stargazersCount)
        assert(model.watchersCount == item.watchersCount)
        assert(model.updatedAt == item.updatedAt)
    }
}