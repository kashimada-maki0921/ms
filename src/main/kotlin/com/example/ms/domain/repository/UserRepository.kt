package com.example.ms.domain.repository

import com.example.ms.domain.model.UserModel

interface UserRepository {
    fun insertUserInfo(userModel: UserModel) :UserModel
}