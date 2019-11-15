package com.example.ms.domain.model

/**
 * ユーザー身体モデル
 */
data class UserBodyModel(
        val userId :Long,
        val sex :String,
        val height: Int,
        val weight: Int
)