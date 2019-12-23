package com.example.ms.app.controller

import com.example.ms.app.form.UserBodyForm
import com.example.ms.domain.model.UserBodyModel
import com.example.ms.domain.service.UserBodyService
import com.example.proto.user.UserBodyProto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["users"])
@Api(tags = ["ユーザー身体情報"])
class UserBodyController(private val userService: UserBodyService) {
    @GetMapping(path = ["{user_id}"])
    @ApiOperation(value = "ユーザー身体情報取得")
    fun userBody(@PathVariable(value = "user_id") userId: Long): ResponseEntity<UserBodyProto.User> {
        val user = userService.findUserBy(userId)

        val userBodyProto = UserBodyProto.User.newBuilder()
                .setUserId(user.userId)
                .setSex(user.sex)
                .setHeight(user.height)
                .setWeight(user.weight)
                .build()
        return ResponseEntity.ok(userBodyProto)
    }

    /**
     * ユーザー身体情報を登録
     */
    @PostMapping(path = ["body"])
    @ApiOperation(value = "ユーザー身体情報登録")
    fun addUser(@RequestBody userBodyForm: UserBodyForm): ResponseEntity<UserBodyProto.User> {
        val user = userService.insertUserBodyInfo(userBodyForm.let {
            UserBodyModel(
                    it.userId,
                    it.sex,
                    it.height,
                    it.weight
            )
        })

        val userBodyProto = UserBodyProto.User.newBuilder()
                .setUserId(user.userId)
                .setSex(user.sex)
                .setHeight(user.height)
                .setWeight(user.weight)
                .build()
        return ResponseEntity.ok(userBodyProto)
    }
}