package org.elteq.logic.users.api

import io.quarkus.security.Authenticated
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.logic.users.models.*
import org.elteq.logic.users.spec.UserSpec

@Path("/api/v1/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "SecurityScheme")
@Authenticated
@Tag(name = "Users", description = "Users")
interface UserApi {

    @POST
    fun add(@Valid dto: UserAddDTO): ApiResponse<UserDTO>

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: String): ApiResponse<UserDTO>

    @GET
    fun search(@BeanParam spec: UserSpec): ApiResponse<List<UserDTO>>

    @PUT
    @Path("/name")
    fun updateName(dto: UserUpdateNameDTO): ApiResponse<UserDTO>

    @PUT
    @Path("/contact")
    fun updateContact(dto: UserUpdateContactDTO): ApiResponse<UserDTO>

    @GET
    @Path("/{contact}")
    fun getByContact(@PathParam("contact") contact: String): ApiResponse<UserDTO>

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id:String): ApiResponse<String>

    @PUT
    @Path("/status")
    fun updateStatus(dto: UserUpdateStatusDTO): ApiResponse<UserDTO>

    @DELETE
    fun deleteAll(): ApiResponse<String>

    @GET
    @Path("/total")
    fun count(): Response

    @GET
    @Path("/export")
    @Produces(value = [MediaType.WILDCARD, MediaType.MEDIA_TYPE_WILDCARD])
    fun export(@BeanParam spec: UserSpec): Response


}