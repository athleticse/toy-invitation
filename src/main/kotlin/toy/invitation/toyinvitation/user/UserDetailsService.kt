package toy.invitation.toyinvitation.user

interface UserDetailsService {
    fun loadUserByUsername(username: String): User
}