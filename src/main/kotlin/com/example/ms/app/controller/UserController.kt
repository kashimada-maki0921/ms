import com.example.ms.app.form.UserBodyForm
import com.example.ms.domain.model.UserModel
import com.example.ms.domain.service.UserService
import com.example.proto.user.UserProto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["users"])
@Api(tags = ["ユーザー情報"])
class UserController(private val userService: UserService) {

    /**
     * ユーザー情報を全検索し、取得結果をProto形式で返却
     *
     * @return ユーザー情報
     */
    @PostMapping
    @ApiOperation(value = "ユーザー情報一覧取得")
    fun addUser(@RequestBody userBodyForm: UserBodyForm): ResponseEntity<UserProto.UserBody> {
        val a = userService.insertUser(userBodyForm.let { UserModel(it.userId,it.sex,it.height,it.weight)})

        val b = UserProto.UserBody.newBuilder()
                .setUserId(a.userId)
                .setSex(a.sex)
                .setHeight(a.height)
                .setWeight(a.weight)
                .build()
        return ResponseEntity.ok(b)
    }
}
