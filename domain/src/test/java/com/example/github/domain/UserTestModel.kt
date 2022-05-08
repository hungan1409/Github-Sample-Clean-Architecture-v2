package com.example.github.domain

import com.example.github.domain.model.User

fun createUser(): User = User(
    "https://avatars1.githubusercontent.com/u/1342004?v=4",
    "https://opensource.google/",
    "opensource@google.com",
    1342004,
    "Google",
    1765
)