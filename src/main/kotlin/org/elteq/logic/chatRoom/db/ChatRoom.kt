package org.elteq.logic.chatRoom.db

import com.fasterxml.jackson.annotation.JsonIgnore
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.json.bind.annotation.JsonbTransient
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import org.elteq.logic.messages.db.Messages
import org.elteq.logic.users.db.Users
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(
    name = "tbl_chat room",
//    indexes = [
//        Index(name = "index_doctor_email", columnList = "email", unique = true),
//        Index(name = "index_doctor_msisdn", columnList = "phone_number", unique = true)
//    ]
)
class ChatRoom : PanacheEntityBase(), Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @JsonIgnore
    @JsonbTransient
    @OneToMany(mappedBy = "chatRoom", cascade = [ALL], fetch = FetchType.EAGER)
    var users:MutableSet<Users>?= mutableSetOf()

    @OneToMany(mappedBy = "chatRoom", cascade = [ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    var messages:MutableSet<Messages>?= mutableSetOf()

    @CreationTimestamp
    @Column(name = "created_on")
    var createdOn: LocalDateTime? = null

    @UpdateTimestamp
    @Column(name = "updated_on")
    var updatedOn: LocalDateTime? = null

    override fun toString(): String {
        return "ChatRoom(id=$id, createdOn=$createdOn, updatedOn=$updatedOn)"
    }


}