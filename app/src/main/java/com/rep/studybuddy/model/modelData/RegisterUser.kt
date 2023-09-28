package com.rep.studybuddy.model.modelData

data class RegisterUser(
    val name: String,
    val lastName: String,
    val email: String,
    val dateBirth: String,
    val password: String,
    val numberId: Number,
    val typeId: String,
    val course: Number,
    val program: String
)
