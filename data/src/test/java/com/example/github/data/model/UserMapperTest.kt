package com.example.github.data.model

import com.example.github.data.createUser
import com.example.github.data.createUserEntity
import org.junit.Before
import org.junit.Test

class UserMapperTest {
    private lateinit var userEntityMapper: UserEntityMapper

    @Before
    fun setup() {
        userEntityMapper = UserEntityMapper()
    }

    @Test
    fun mapEntityToDomainTest() {
        // generate user entity
        val entity = createUserEntity()
        // mapper
        val model = userEntityMapper.mapToDomain(entity)

        assert(entity.id == model.id)
        assert(entity.avatarUrl == model.avatarUrl)
        assert(entity.blog == model.blog)
        assert(entity.name == model.name)
        assert(entity.email == model.email)
        assert(entity.publicRepos == model.publicRepos)
    }

    @Test
    fun mapDomainToEntityTest() {
        // generate model
        val model = createUser()

        // mapper to entity
        val entity = userEntityMapper.mapToEntity(model)

        assert(entity.id == model.id)
        assert(entity.avatarUrl == model.avatarUrl)
        assert(entity.blog == model.blog)
        assert(entity.name == model.name)
        assert(entity.email == model.email)
        assert(entity.publicRepos == model.publicRepos)
    }
}