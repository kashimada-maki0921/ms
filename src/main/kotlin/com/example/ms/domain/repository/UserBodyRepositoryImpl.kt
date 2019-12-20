package com.example.ms.domain.repository

import com.example.ms.domain.model.UserBodyModel
import com.example.ms.infrastructure.dao.UserBodyDao
import com.example.ms.infrastructure.entity.UserBodyEntity
import org.springframework.stereotype.Repository

/**
 * ユーザー身体情報サービス実行クラス
 */
@Repository
class UserBodyRepositoryImpl(private val userBodyDao: UserBodyDao) : UserBodyRepository {
    override fun insertUserBodyInfo(userBodyModel: UserBodyModel): UserBodyModel {
        userBodyDao.insertUsersBody(userBodyModel.convertIntoUserEntity())
        return userBodyModel
    }

    private fun UserBodyModel.convertIntoUserEntity(): UserBodyEntity {
        return this.let {
            UserBodyEntity(
                    it.userId,
                    it.sex,
                    it.height,
                    it.weight
            )
        }
    }
}