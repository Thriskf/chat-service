package org.elteq.logic.chatRoom.api

import io.quarkus.security.Authenticated
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.logic.chatRoom.dtos.*
import org.elteq.logic.chatRoom.enums.ChatRoomType
import org.elteq.logic.chatRoom.spec.ChatRoomSpec

@Path("/api/v1/chat-room")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "SecurityScheme")
@Authenticated
@Tag(name = "Chat Room", description = "Chat Room")
interface ChatRoomApi {

    @POST
    fun add(@Valid dto: ChatRoomAddDTO): ChatRoomResponse

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: String): ApiResponse<ChatRoomDTO>

    @GET
    fun search(@BeanParam spec: ChatRoomSpec): ChatRoomPaginatedResponse

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: String): ApiResponse<String>


    @DELETE
    fun deleteAll(): ApiResponse<String>

    @GET
    @Path("/total")
    fun count(): Response

    @GET
    @Path("/user/{id}")
    fun getByUserId(@PathParam("id") userId: String): ApiResponse<List<ChatRoomDTO>>

    @GET
    @Path("/name/{name}")
    fun getByName(@PathParam("name") name: String): ApiResponse<List<ChatRoomDTO>>

    @GET
    @Path("/type/{type}")
    fun getByType(@PathParam("type") type: ChatRoomType): ApiResponse<List<ChatRoomDTO>>

    @PUT
    @Path("/add-member")
    fun addGroupMember(dto: ChatRoomAddMemberDTO): ApiResponse<ChatRoomDTO>

    @PUT
    @Path("/remove-member")
    fun removeGroupMember(dto: ChatRoomRMMemberDTO): ApiResponse<ChatRoomDTO>

    @Path("/change-name")
    fun changeGroupName(dto: ChatRoomChangeNameDTO): ApiResponse<ChatRoomDTO>


}