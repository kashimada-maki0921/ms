package com.example.ms.domain.repository

import com.example.ms.domain.model.UserBodyModel

interface UserBodyRepository {
    fun insertUserBodyInfo(userBodyModel: UserBodyModel) :UserBodyModel
}