package com.example.ms.domain.repository

import com.example.ms.domain.model.UserModel
import com.example.ms.infrastructure.dao.UserDao
import com.example.ms.infrastructure.entity.UserEntity
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override fun insertUserInfo(userModel: UserModel): UserModel {
        userDao.insertUsersBody(userModel.convertIntoUserEntity())
        return userModel
    }

    private fun UserModel.convertIntoUserEntity(): UserEntity {
        return this.let {
            UserEntity(
                    it.userId,
                    it.sex,
                    it.height,
                    it.weight
            )
        }
    }
}