package toy.invitation.toyinvitation.config

import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService, private val passwordEncoder: PasswordEncoder) {

    @PostMapping("/register")
    fun register(@RequestBody userRegisterReq: UserRegisterReq): ResponseEntity<UserRegisterRes> {

        if(userService.existsUser(userRegisterReq.email)) {
            throw BaseException(BaseResponseCode.DUPLICATE_EMAIL)
        }
        userRegisterReq.password = passwordEncoder.encode(userRegisterReq.password)

        return ResponseEntity.ok(userService.createUser(userRegisterReq))
    }

    @PostMapping("/login")
    fun login(@RequestBody userLoginReq: UserLoginReq): ResponseEntity<UserLoginRes> {
        if(!userService.existsUser(userLoginReq.email)) {
            throw BaseException(BaseResponseCode.USER_NOT_FOUND)
        }

        val user: User = userService.findUser(userLoginReq.email)

        if(!passwordEncoder.matches(userLoginReq.password, user.password)) {
            throw BaseException(BaseResponseCode.INVALID_PASSWORD)
        }

        return ResponseEntity.ok(userService.login(userLoginReq))
    }

}