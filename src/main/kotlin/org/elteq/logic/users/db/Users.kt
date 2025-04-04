package org.elteq.logic.users.db

import com.fasterxml.jackson.annotation.JsonIgnore
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.json.bind.annotation.JsonbTransient
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import org.elteq.logic.chatRoom.models.ChatRoom
import org.elteq.logic.contacts.db.Contact
import org.elteq.logic.messages.db.Messages
import org.elteq.logic.users.enums.Gender
import org.elteq.logic.users.enums.Status
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(
    name = "tbl_users",
    indexes = [
        Index(name = "idx_deleted", columnList = "deleted"),
//        Index(name = "index_doctor_msisdn", columnList = "phone_number", unique = true)
    ]
)
class Users : PanacheEntityBase(), Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null

    @Column(name = "first_name", nullable = false)
    var firstName: String? = null

    @Column(name = "last_name", nullable = false)
    var lastName: String? = null

    @Column(name = "other_name")
    var otherName: String? = null

    @Column(name = "display_name")
    var displayName: String? = null

    @JsonIgnore
    @JsonbTransient
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER, cascade = [ALL])
    var chatRooms: MutableSet<ChatRoom>? = mutableSetOf()

    @JsonIgnore
    @JsonbTransient
    @OneToMany(mappedBy = "user", cascade = [ALL], fetch = FetchType.LAZY)
//    @JoinColumn(name = "us", referencedColumnName = "id")
    var messages: MutableSet<Messages>? = mutableSetOf()


    @OneToMany(mappedBy = "user", cascade = [ALL], fetch = FetchType.EAGER, targetEntity = Contact::class, orphanRemoval = true)
    var contacts: Set<Contact>? = setOf()

//    @OneToOne(mappedBy = "user", cascade = [ALL], fetch = FetchType.EAGER, orphanRemoval = true)
//    @JoinColumn(name = "date_of_birth_id", referencedColumnName = "id") // Foreign key column
//    var dateOfBirth: DOB? = null

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    var gender: Gender? = null

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: Status? = Status.UNVERIFIED

    @Column(name = "deleted")
    var deleted: Boolean = false

    @CreationTimestamp
    @Column(name = "created_on")
    var createdOn: LocalDateTime? = null

    @UpdateTimestamp
    @Column(name = "updated_on")
    var updatedOn: LocalDateTime? = null

    override fun toString(): String {
        return "Users(createdOn=$createdOn, id=$id, firstName=$firstName, lastName=$lastName, otherName=$otherName, gender=$gender, " +
                "status=$status, deleted=$deleted " +
                "displayName=$displayName, updatedOn=$updatedOn, gender=$gender)"
    }

}