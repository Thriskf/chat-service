package org.elteq.logic.chatRoom.models

import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import io.quarkus.panache.common.Sort
import jakarta.enterprise.context.ApplicationScoped
import org.elteq.base.utils.queryUtils.PaginatedQuery
import org.elteq.logic.chatRoom.enums.ChatRoomType
import org.elteq.logic.chatRoom.spec.ChatRoomSpec

@ApplicationScoped
class ChatRoomRepository : PanacheRepositoryBase<ChatRoom, String> {

    private val paginatedQuery: PaginatedQuery = PaginatedQuery()

    fun findByType(type: ChatRoomType): PanacheQuery<ChatRoom> {
        return find("type", type)
    }

    fun findByName(name: String): PanacheQuery<ChatRoom> {
        return find("name", name)
    }

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

    fun findByUserId(userId: String): PanacheQuery<ChatRoom> {
        val sort = Sort.descending("updatedOn")

        return find(
            """
        SELECT c FROM ChatRoom c
        JOIN c.users u
        WHERE u.id = :userId
        """, sort, mapOf("userId" to userId)
        )
    }

    fun findByRoomIdAndUserId(roomId: String, userId: String): ChatRoom? {
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


    fun filter(spec: ChatRoomSpec, operation: String = "and"): PanacheQuery<ChatRoom> {
//        if (spec.sortBy == "createdOn") spec.sortBy = "createdOn"
        return paginatedQuery.toQuery(spec, operation, this)
    }
}