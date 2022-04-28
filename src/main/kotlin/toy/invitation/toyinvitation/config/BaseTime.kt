package toy.invitation.toyinvitation.config

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTime {

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdDateTime: LocalDateTime? = null

    @UpdateTimestamp
    @Column(nullable = false)
    val updatedDateTime: LocalDateTime? = null
}