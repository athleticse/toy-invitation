package toy.invitation.toyinvitation.config

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(username: String): Optional<User>
    fun existsByEmail(username: String): Boolean
}