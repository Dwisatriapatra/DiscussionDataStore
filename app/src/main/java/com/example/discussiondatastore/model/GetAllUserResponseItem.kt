package com.example.discussiondatastore.model

data class GetAllUserResponseItem(
    val address: String,
    val id: String,
    val image: String,
    val name: String,
    val pass: String,
    val password: String,
    val umur: String,
    val username: String
)