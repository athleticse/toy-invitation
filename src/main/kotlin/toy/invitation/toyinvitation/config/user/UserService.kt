package toy.invitation.toyinvitation.config.user

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import toy.invitation.toyinvitation.config.JwtTokenProvider
import toy.invitation.toyinvitation.config.req.UserLoginReq
import toy.invitation.toyinvitation.config.req.UserRegisterReq
import toy.invitation.toyinvitation.config.req.UserRegisterRes
import toy.invitation.toyinvitation.config.res.BaseException
import toy.invitation.toyinvitation.config.res.BaseResponseCode
import toy.invitation.toyinvitation.config.res.UserLoginRes

@Service
class UserService(private val userRepository: UserRepository, private val jwtTokenProvider: JwtTokenProvider) {

    fun findUser(email: String): User {
        return userRepository.findByEmail(email).orElseThrow{ BaseException(BaseResponseCode.USER_NOT_FOUND) }
    }

    fun existsUser(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    fun createUser(userRegisterReq: UserRegisterReq): UserRegisterRes {
        val user = User(userRegisterReq.name, userRegisterReq.email, userRegisterReq.password)
        userRepository.save(user)

        return UserRegisterRes(user.id, user.email)
    }

    fun login(userLoginReq: UserLoginReq): UserLoginRes {
        val token: String = jwtTokenProvider.createToken(userLoginReq.email)

        return UserLoginRes(HttpStatus.OK, token)
    }
}