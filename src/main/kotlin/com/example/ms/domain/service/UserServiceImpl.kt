package com.example.ms.domain.service

import com.example.ms.domain.model.UserModel
import com.example.ms.domain.repository.UserRepository
import org.springframework.stereotype.Service

/**
 * ユーザー情報サービス実行クラス
 */
@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun insertUser(userModel: UserModel):UserModel {
       return userRepository.insertUserInfo(userModel)
    }
}