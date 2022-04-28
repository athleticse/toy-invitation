package toy.invitation.toyinvitation.config.user

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import toy.invitation.toyinvitation.config.res.BaseException
import toy.invitation.toyinvitation.config.res.BaseResponseCode

@Service
class UserDetailService(private val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username).orElseThrow{ BaseException(BaseResponseCode.USER_NOT_FOUND) }
    }
}