package org.elteq.logic.contacts.db

import com.fasterxml.jackson.annotation.JsonIgnore
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.json.bind.annotation.JsonbTransient
import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.elteq.logic.contacts.enums.ContactType
import org.elteq.logic.users.db.Users
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(
    name = "tbl_contacts",
    indexes = [
        Index(name = "idx_contact_user_id", columnList = "user_id"),
        Index(name = "idx_contact_value", columnList = "contact_value"),
        Index(name = "idx_contact_type", columnList = "type"),
        Index(name = "idx_deleted", columnList = "deleted"),

    ]
)
class Contact : PanacheEntityBase(), Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null

    @JsonIgnore
    @JsonbTransient
    @ManyToOne(cascade = [CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST], fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: Users? = null

    @Enumerated(value = EnumType.STRING)
    @Column
    var type: ContactType? = null

    @NotEmpty
    @Column(name = "contact_value")
    var value: String = ""

    @NotNull
    @Column(name = "is_contactable")
    var isContactable: Boolean = true

    @Column(name = "deleted")
    var deleted: Boolean = false

    @CreationTimestamp
    @Column(name = "created_on")
    var createdOn: LocalDateTime? = null

    @UpdateTimestamp
    @Column(name = "updated_on")
    var updatedOn: LocalDateTime? = null

    @Version
    val version: Long = 0
    override fun toString(): String {
        return "Contact(id=$id, type=$type, value='$value', isContactable=$isContactable, createdOn=$createdOn, updatedOn=$updatedOn, version=$version)"
    }

}