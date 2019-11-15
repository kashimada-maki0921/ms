package com.example.ms.infrastructure.entity

import org.seasar.doma.*
import org.seasar.doma.jdbc.entity.NamingType

/**
 * ユーザー情報エンティティ
 */
@Entity(immutable = true, naming = NamingType.SNAKE_LOWER_CASE)
data class UserEntity(
        /** ユーザーID */
        @Id
        @Column(name = "id")
        val userId: Long,

        /** テストユーザーフラグ */
        @Column(name = "sex")
        val sex: String,

        /** トークン */
        @Column(name = "height")
        val height: Int,

        @Column(name = "weight")
        val weight: Int
)