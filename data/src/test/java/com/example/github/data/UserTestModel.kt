package com.example.github.data

import com.example.github.data.model.UserEntity
import com.example.github.domain.model.User

fun createUserEntity(): UserEntity = UserEntity(
    "https://avatars1.githubusercontent.com/u/1342004?v=4",
    "https://www.google.com.vn",
    "githubsource@google.com",
    1342010,
    "Youtube",
    1565
)

fun createUser(): User = User(
    "https://avatars1.githubusercontent.com/u/1342004?v=4",
    "https://opensource.google/",
    "opensource@google.com",
    1342004,
    "Google",
    1765
)