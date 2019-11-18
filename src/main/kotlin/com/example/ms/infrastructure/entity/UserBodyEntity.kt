package com.example.ms.infrastructure.entity

import org.seasar.doma.*
import org.seasar.doma.jdbc.entity.NamingType

/**
 * ユーザー情報エンティティ
 */
@Entity(immutable = true, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "users_body")
data class UserBodyEntity(
        /** ユーザーID */
        @Id
        @Column(name = "user_id")
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