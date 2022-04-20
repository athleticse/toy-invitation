package toy.invitation.toyinvitation.config

data class UserRegisterReq (
    val id: Long,
    val name: String,
    val email: String,
    var password: String

)