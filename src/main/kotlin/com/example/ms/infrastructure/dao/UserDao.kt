package com.example.ms.infrastructure.dao

import com.example.ms.infrastructure.entity.UserEntity
import org.seasar.doma.*
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.jdbc.Result
import org.springframework.stereotype.Repository

@Dao
@Repository
@ConfigAutowireable
interface UserDao {
    @Insert
    fun insertUsersBody(userEntity: UserEntity): Result<UserEntity>
}
