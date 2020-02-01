package ru.aumsu.www.application.models

class Message (
    val from: Int,
    val toEntity: Int,
    val message: String,
    val id: Int?
)