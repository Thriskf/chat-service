package org.elteq.logic.auth.api

import io.quarkus.security.Authenticated
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.elteq.logic.users.dtos.*

@Authenticated
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v1/-auth")
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "SecurityScheme")

interface AuthApi {
    @POST
    @Path("/login")
    fun login(@Valid dto: LoginDTO): UserLoginResponse

    @PATCH
      @Path("/reset-password")
    fun resetPassword(@Valid dto: UserForgetPasswordDTO): UserResponse

    @PATCH
    @Path("/update-password")
    fun updatePassword(@Valid dto:UserChangePasswordDTO): UserResponse

    @POST
    @Path("logout")
    fun logout()
}