package toy.invitation.toyinvitation.config

import org.springframework.http.HttpStatus

data class UserLoginRes(
    val httpStatus: HttpStatus,
    val token: String
)
