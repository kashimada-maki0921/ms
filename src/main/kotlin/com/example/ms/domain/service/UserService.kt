package com.example.ms.domain.service

import com.example.ms.domain.model.UserModel

interface UserService{
    fun insertUser(userModel: UserModel) :UserModel
}