package org.elteq.logic.chatRoom.db

import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import org.elteq.base.utils.PaginatedQuery
import org.elteq.logic.chatRoom.spec.ChatRoomSpec
import java.util.*

@ApplicationScoped
class ChatRoomRepository : PanacheRepositoryBase<ChatRoom, UUID> {

    private val paginatedQuery: PaginatedQuery = PaginatedQuery()


//    fun findByRoomIdAndUserId(roomId: UUID, userId: UUID): ChatRoom? {
//        return find(
//            """
//            SELECT * FROM chat_room c
//            JOIN users u ON u.chat_room_id = c.id
//            WHERE c.id = :roomId
//            AND u.id = :userId
//            """,
//            mapOf("roomId" to roomId, "userId" to userId)
//        ).firstResult()
//    }

    fun findByRoomIdAndUserId(roomId: UUID, userId: UUID): ChatRoom? {
        return find(
            """
        SELECT c FROM ChatRoom c
        JOIN c.users u
        WHERE c.id = :roomId
        AND u.id = :userId
        """,
            mapOf("roomId" to roomId, "userId" to userId)
        ).firstResult()
    }


    fun all(spec: ChatRoomSpec, operation: String = "and"): PanacheQuery<ChatRoom> {
//        if (spec.sortBy == "createdOn") spec.sortBy = "createdOn"
        return paginatedQuery.toQuery(spec, operation, this)
    }
}