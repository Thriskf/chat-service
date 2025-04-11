package org.elteq.logic.users.models

import com.fasterxml.jackson.annotation.JsonIgnore
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.json.bind.annotation.JsonbTransient
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(
    name = "tbl_user_credentials",
//    indexes = [
//        Index(name = "idx_deleted", columnList = "deleted"),
//        Index(name = "idx_email", columnList = "email", unique = true),
////        Index(name = "index_doctor_msisdn", columnList = "phone_number", unique = true)
//    ]
)
class Credentials : PanacheEntityBase(), Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null

    @JsonIgnore
    @JsonbTransient
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: Users? = null

    @Column(name = "password", nullable = false)
    var password: String? = null

    @Column(name = "expires_in", nullable = false)
    var expiresIn: LocalDateTime? = null

    @CreationTimestamp
    @Column(name = "created_on")
    var createdOn: LocalDateTime? = null

    @UpdateTimestamp
    @Column(name = "updated_on")
    var updatedOn: LocalDateTime? = null

}