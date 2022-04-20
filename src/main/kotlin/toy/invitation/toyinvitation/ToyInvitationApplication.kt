package toy.invitation.toyinvitation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class ToyInvitationApplication

fun main(args: Array<String>) {
	runApplication<ToyInvitationApplication>(*args)
}
