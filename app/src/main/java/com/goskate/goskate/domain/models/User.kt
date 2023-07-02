package com.goskate.goskate.domain.models

data class User(
    val uid: String = String(),
    val name: String = String(),
    val gmail: String = String(),
    val password: String = String(),
    val gender: String = String(),
    val age: String = String(),
)
