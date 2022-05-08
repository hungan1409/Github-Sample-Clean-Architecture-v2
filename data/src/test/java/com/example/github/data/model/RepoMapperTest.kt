package com.example.github.data.model

import com.example.github.data.createRepo
import com.example.github.data.createRepoEntity
import org.junit.Before
import org.junit.Test

class RepoMapperTest {
    private lateinit var repoEntityMapper: RepoEntityMapper

    @Before
    fun setup() {
        repoEntityMapper = RepoEntityMapper()
    }

    @Test
    fun mapEntityToDomainTest() {
        val repoEntity = createRepoEntity()
        val item = repoEntityMapper.mapToDomain(repoEntity)

        assert(repoEntity.id == item.id)
        assert(repoEntity.fullName == item.fullName)
        assert(repoEntity.description == item.description)
        assert(repoEntity.language == item.language)
        assert(repoEntity.htmlUrl == item.htmlUrl)
        assert(repoEntity.forksCount == item.forksCount)
        assert(repoEntity.stargazersCount == item.stargazersCount)
        assert(repoEntity.watchersCount == item.watchersCount)
        assert(repoEntity.updatedAt == item.updatedAt)
    }

    @Test
    fun mapDomainToEntityTest() {
        val repoItem = createRepo()
        val repoItemEntity = repoEntityMapper.mapToEntity(repoItem)

        assert(repoItem.id == repoItemEntity.id)
        assert(repoItem.fullName == repoItemEntity.fullName)
        assert(repoItem.description == repoItemEntity.description)
        assert(repoItem.language == repoItemEntity.language)
        assert(repoItem.htmlUrl == repoItemEntity.htmlUrl)
        assert(repoItem.forksCount == repoItemEntity.forksCount)
        assert(repoItem.stargazersCount == repoItemEntity.stargazersCount)
        assert(repoItem.watchersCount == repoItemEntity.watchersCount)
        assert(repoItem.updatedAt == repoItemEntity.updatedAt)
    }
}