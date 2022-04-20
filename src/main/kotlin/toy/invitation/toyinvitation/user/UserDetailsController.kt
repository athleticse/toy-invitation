package toy.invitation.toyinvitation.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import toy.invitation.toyinvitation.config.JwtAuthenticationProvider
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
class UserDetailsController @Autowired constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtAuthenticationProvider: JwtAuthenticationProvider,
) {

    @PostMapping("/login")
    fun login(@RequestBody user: UserDTO, response: HttpServletResponse): UserDTO? {
        val member = userRepository.findByEmail(user.email)
            .orElseThrow {
                IllegalArgumentException(
                    "가입되지 않은 E-MAIL 입니다."
                )
            }

        require(passwordEncoder.matches(user.passwd, member.password)) { "잘못된 비밀번호입니다." }
        val token = jwtAuthenticationProvider.createToken(member.username, member.getRoles())
        response.setHeader("X-AUTH-TOKEN", token)
        val cookie = Cookie("X-AUTH-TOKEN", token)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.secure = true
        response.addCookie(cookie)
        return UserDTO(
            id = member.getId(),
            passwd = member.password,
            email = member.username,
            phoneNumber = member.getPhoneNumber(),
            name = member.getName()
        )
    }

}