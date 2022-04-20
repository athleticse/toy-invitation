package toy.invitation.toyinvitation.user

data class UserDTO(
    val id: Long,
    val passwd: String,
    val email: String,
    val phoneNumber: String,
    val name: String
)
