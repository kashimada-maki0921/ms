package com.example.ms.domain.service

import com.example.ms.domain.model.UserBodyModel
import com.example.ms.domain.repository.UserBodyRepository
import org.springframework.stereotype.Service

/**
 * ユーザー身体情報サービス実行クラス
 */
@Service
class UserBodyServiceImpl(private val userRepository: UserBodyRepository) : UserBodyService {
    override fun insertUserBodyInfo(userBodyModel: UserBodyModel):UserBodyModel {
       return userRepository.insertUserBodyInfo(userBodyModel)
    }
}