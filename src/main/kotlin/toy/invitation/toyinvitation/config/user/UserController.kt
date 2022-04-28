package toy.invitation.toyinvitation.config.user

import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import toy.invitation.toyinvitation.config.req.UserLoginReq
import toy.invitation.toyinvitation.config.req.UserRegisterReq
import toy.invitation.toyinvitation.config.req.UserRegisterRes
import toy.invitation.toyinvitation.config.res.BaseException
import toy.invitation.toyinvitation.config.res.BaseResponseCode
import toy.invitation.toyinvitation.config.res.UserLoginRes

@RestController
class UserController(private val userService: UserService, private val passwordEncoder: PasswordEncoder) {

    @PostMapping("/register")
    fun register(@RequestBody userRegisterReq: UserRegisterReq): ResponseEntity<UserRegisterRes> {
        // 중복된 이메일인지 확인
        if(userService.existsUser(userRegisterReq.email)) {
            throw BaseException(BaseResponseCode.DUPLICATE_EMAIL)
        }
        // 비밀번호 암호화
        userRegisterReq.password = passwordEncoder.encode(userRegisterReq.password)
        // 계정 생성 및 토큰 생성
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