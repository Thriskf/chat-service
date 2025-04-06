package org.elteq.logic.chatRoom.models

import com.fasterxml.jackson.annotation.JsonIgnore
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.json.bind.annotation.JsonbTransient
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import org.elteq.logic.chatRoom.enums.ChatRoomType
import org.elteq.logic.messages.models.Messages
import org.elteq.logic.users.models.Users
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(
    name = "tbl_chat room",
    indexes = [
        Index(name = "index_name", columnList = "name"),
        Index(name = "index_type", columnList = "type"),
        Index(name = "idx_deleted", columnList = "deleted")
    ]
)
class ChatRoom : PanacheEntityBase(), Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null

    @JsonIgnore
    @JsonbTransient
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_chat_rooms",
        joinColumns = [JoinColumn(name = "chat_room_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var users: MutableSet<Users>? = mutableSetOf()

    @OneToMany(mappedBy = "chatRoom", cascade = [ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    var messages: MutableSet<Messages>? = mutableSetOf()

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    var type: ChatRoomType? = null

    @Column(name = "name", length = 100)
    var name: String? = null

    @Column(name = "deleted")
    var deleted: Boolean = false

    @CreationTimestamp
    @Column(name = "created_on")
    var createdOn: LocalDateTime? = null

    @UpdateTimestamp
    @Column(name = "updated_on")
    var updatedOn: LocalDateTime? = null

    override fun toString(): String {
        return "ChatRoom(id=$id, createdOn=$createdOn, deleted=$deleted, updatedOn=$updatedOn, type=$type, name=$name)"
    }


}