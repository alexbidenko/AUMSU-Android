package ru.aumsu.www.application.models

class User (
    val firstName: String?,
    val lastName: String?,
    val login: String,
    val password: String,
    val token: String,
    val avatar: String?,
    val status: Int,
    val number: String?,
    val patronymic: String?,
    val data: String?,
    val master: String?,
    val entities: Array<Entity>,
    val id: Int?
)