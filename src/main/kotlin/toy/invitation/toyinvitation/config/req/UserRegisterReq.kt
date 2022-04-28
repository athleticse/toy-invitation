package toy.invitation.toyinvitation.config.req

data class UserRegisterReq (
    val id: Long,
    val name: String,
    val email: String,
    var password: String

)