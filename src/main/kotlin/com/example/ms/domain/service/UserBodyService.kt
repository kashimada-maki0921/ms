package com.example.ms.domain.service

import com.example.ms.domain.model.UserBodyModel

interface UserBodyService{
    fun insertUserBodyInfo(userBodyModel: UserBodyModel) :UserBodyModel
}