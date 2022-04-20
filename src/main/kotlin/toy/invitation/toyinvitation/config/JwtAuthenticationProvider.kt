package toy.invitation.toyinvitation.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils
import toy.invitation.toyinvitation.user.User
import toy.invitation.toyinvitation.user.UserDetailsService
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest


@Component
class JwtAuthenticationProvider @Autowired constructor(
    private val userDetailsService: UserDetailsService
) {

    private val secretKey = "secret"
    private val tokenValidTime = 1000L * 60 * 60

    fun createToken(userPk: String, roles: List<String>): String {
        val claims = Jwts.claims().setSubject(userPk)
        claims["roles"] = roles
        val now = Date()
        return Jwts.builder()
            .setClaims(claims) // 정보 저장
            .setIssuedAt(now) // 토큰 발행 시간 정보
            .setExpiration(Date(now.time + tokenValidTime)) // set Expire Time
            .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
            // signature 에 들어갈 secret값 세팅
            .compact()
    }

    // JWT 토큰에서 인증 정보 조회
    fun getAuthentication(token: String): Authentication {
        val userDetails: User = userDetailsService.loadUserByUsername(this.getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    // 토큰에서 회원 정보 추출
    private fun getUserPk(token: String): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    fun resolveToken(request: HttpServletRequest?): String? {
        var token: String? = null
        val cookie: Cookie? = WebUtils.getCookie(request!!, "X-AUTH-TOKEN")
        if (cookie != null) token = cookie.value
        return token
    }

    // 토큰의 유효성 + 만료일자 확인
    fun validateToken(jwtToken: String?): Boolean {
        return try {
            val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }
}