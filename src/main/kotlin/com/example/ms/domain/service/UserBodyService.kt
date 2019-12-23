package com.example.ms.domain.service

import com.example.ms.domain.model.UserBodyModel

interface UserBodyService {
    fun findUserBy(userId: Long): UserBodyModel
    fun insertUserBodyInfo(userBodyModel: UserBodyModel): UserBodyModel
}