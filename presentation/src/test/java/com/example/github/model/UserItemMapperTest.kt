package com.example.github.model

import com.example.github.createUser
import com.example.github.createUserItem
import org.junit.Before
import org.junit.Test

class UserItemMapperTest {
    private lateinit var userItemMapper: UserItemMapper

    @Before
    fun setup() {
        userItemMapper = UserItemMapper()
    }

    @Test
    fun mapPresentationToDomainTest() {
        // generate user
        val item = createUserItem()

        // mapper to domain
        val model = userItemMapper.mapToDomain(item)

        assert(item.id == model.id)
        assert(item.avatarUrl == model.avatarUrl)
        assert(item.blog == model.blog)
        assert(item.name == model.name)
        assert(item.email == model.email)
        assert(item.publicRepos == model.publicRepos)
    }

    @Test
    fun mapDomainToPresentationTest() {
        // generate entity model
        val model = createUser()

        // mapper to presentation
        val item = userItemMapper.mapToPresentation(model)

        assert(item.id == model.id)
        assert(item.avatarUrl == model.avatarUrl)
        assert(item.blog == model.blog)
        assert(item.name == model.name)
        assert(item.email == model.email)
        assert(item.publicRepos == model.publicRepos)
    }
}