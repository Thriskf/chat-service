package org.elteq.logic.messages.api

import io.quarkus.security.Authenticated
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.logic.messages.dtos.MessageAddDTO
import org.elteq.logic.messages.dtos.MessageDTO
import org.elteq.logic.messages.spec.MessageSpec

@Path("/api/v1/message")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "SecurityScheme")
@Authenticated
@Tag(name = "Message", description = "Message")
interface MessageApi {

    @POST
    fun add(@Valid dto: MessageAddDTO): ApiResponse<MessageDTO>

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: String): ApiResponse<MessageDTO>

    @GET
    fun search(@BeanParam spec: MessageSpec): ApiResponse<List<MessageDTO>>

    @GET
    @Path("/{userId}")
    fun getByUser(@PathParam("userId") userId: String): ApiResponse<List<MessageDTO>>

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: String): Response

    @PUT
    @Path("/status")
    fun updateStatus(dto: Any): ApiResponse<MessageDTO>

    @DELETE
    fun deleteAll(): Response

    @GET
    @Path("/total")
    fun count(): Response


}