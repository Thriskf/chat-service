package org.elteq.logic.messages.api

import io.quarkus.security.Authenticated
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.elteq.base.apiResponse.domain.ApiResponse
import java.util.*

@Path("/api/v1/message")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "SecurityScheme")
@Authenticated
@Tag(name = "Message", description = "Message")
interface MessageApi {

    @POST
    fun add(@Valid dto: Any): ApiResponse<Any>

    @GET
    @Path("/{wardId}")
    fun getById(@PathParam("wardId") wardId: UUID): ApiResponse<Any>

    @GET
    fun search(@BeanParam spec: Any): ApiResponse<List<Any>>

    @PUT
    fun addInCharge(dto: Any): ApiResponse<Any>

    @GET
    @Path("/{email}")
    fun getByContact(@PathParam("email") email: String): Response

    @DELETE
    @Path("/{wardId}")
    fun delete(@PathParam("wardId") wardId: UUID): Response

    @PUT
    @Path("/status")
    fun updateStatus(dto: Any): ApiResponse<Any>

    @DELETE
    fun deleteAll(): Response

    @GET
    @Path("/total")
    fun count(): Response


}