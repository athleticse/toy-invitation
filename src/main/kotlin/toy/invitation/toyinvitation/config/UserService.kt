package toy.invitation.toyinvitation.config

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val jwtTokenProvider: JwtTokenProvider) {

    fun findUser(email: String): User {
        return userRepository.findByEmail(email).orElseThrow{BaseException(BaseResponseCode.USER_NOT_FOUND)}
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