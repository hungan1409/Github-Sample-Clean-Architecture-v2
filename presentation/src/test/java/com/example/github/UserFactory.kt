package com.example.github

import com.example.github.domain.model.User
import com.example.github.model.UserItem

fun createUserItem(): UserItem = UserItem(
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