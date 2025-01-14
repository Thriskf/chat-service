package org.elteq.logic.chatRoom.api

import io.quarkus.security.Authenticated
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.logic.chatRoom.models.ChatRoomAddDTO
import org.elteq.logic.chatRoom.models.ChatRoomDTO
import org.elteq.logic.chatRoom.spec.ChatRoomSpec

@Path("/api/v1/chat-room")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "SecurityScheme")
@Authenticated
@Tag(name = "Chat Room", description = "Chat Room")
interface ChatRoomApi {

    @POST
    fun add(@Valid dto: ChatRoomAddDTO): ApiResponse<ChatRoomDTO>

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: String): ApiResponse<ChatRoomDTO>

    @GET
    fun search(@BeanParam spec: ChatRoomSpec): ApiResponse<List<ChatRoomDTO>>

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: String): ApiResponse<String>


    @DELETE
    fun deleteAll(): ApiResponse<String>

    @GET
    @Path("/total")
    fun count(): Response


}