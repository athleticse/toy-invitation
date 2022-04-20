package toy.invitation.toyinvitation.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors
import javax.persistence.*


@Entity(name="account")
class User: UserDetails {

    @Id
    private var id: Long? = null
    private var passwd: String? = null
    private var email: String? = null
    private var phoneNumber: String? = null
    private var name: String? = null

    @ElementCollection(fetch = FetchType.EAGER)
    private val roles: List<String> = ArrayList()

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return roles.stream()
            .map { role: String? ->
                SimpleGrantedAuthority(
                    role
                )
            }
            .collect(Collectors.toList())
    }

    override fun getPassword(): String {
        return this.passwd!!
    }

    override fun getUsername(): String {
        return this.email!!
    }

    fun getId(): Long {
        return this.id!!
    }

    fun getPhoneNumber(): String {
        return this.phoneNumber!!
    }

    fun getName(): String {
        return this.name!!
    }

    fun getRoles(): List<String> {
        return this.roles
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
