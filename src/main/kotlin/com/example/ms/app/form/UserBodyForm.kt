package com.example.ms.app.form

data class UserBodyForm(
        val userId: Long,
        val sex: String,
        val height: Int,
        val weight: Int
)