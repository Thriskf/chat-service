package org.elteq.logic.messages.db

import com.fasterxml.jackson.annotation.JsonIgnore
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.json.bind.annotation.JsonbTransient
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import org.elteq.logic.chatRoom.models.ChatRoom
import org.elteq.logic.users.db.Users
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(
    name = "tbl_messages",
    indexes = [
        Index(name = "idx_deleted", columnList = "deleted"),
//        Index(name = "index_doctor_msisdn", columnList = "phone_number", unique = true)
    ]
)
class Messages : PanacheEntityBase(), Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null

    @JsonIgnore
    @JsonbTransient
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", nullable = false)
    var chatRoom: ChatRoom? = null

    @Column(name = "message")
    var message: String? = null

    @ManyToOne(cascade = [ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: Users? = null

    @Column(name = "deleted")
    var deleted: Boolean = false

    @CreationTimestamp
    @Column(name = "created_on")
    var createdOn: LocalDateTime? = null

    @UpdateTimestamp
    @Column(name = "updated_on")
    var updatedOn: LocalDateTime? = null

    override fun toString(): String {
        return "Messages(id=$id, deleted=$deleted createdOn=$createdOn, updatedOn=$updatedOn)"
    }
}