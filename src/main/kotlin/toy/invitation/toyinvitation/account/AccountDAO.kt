package toy.invitation.toyinvitation.account

import javax.persistence.*


@Entity(name="account")
data class AccountDAO(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:String
)
