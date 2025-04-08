package org.elteq.logic.auth.api

import io.quarkus.security.Authenticated
import jakarta.annotation.security.PermitAll
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.logic.auth.dtos.LoginDTO
import org.elteq.logic.auth.dtos.RefreshTokenRequest
import org.elteq.logic.auth.dtos.UserChangePasswordDTO
import org.elteq.logic.auth.dtos.UserForgetPasswordDTO
import org.elteq.logic.users.dtos.LoginResponse
import org.elteq.logic.users.dtos.UserAddDTO
import org.elteq.logic.users.dtos.UserDTO
import org.elteq.logic.users.dtos.UserResponse

@Authenticated
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "SecurityScheme")
@Tag(name = "Auth", description = "Auth")

interface AuthApi {

    @POST
    @Path("/signup")
    @PermitAll
    fun register(@Valid dto: UserAddDTO): ApiResponse<UserDTO>

    @POST
    @Path("/login")
    @PermitAll
    fun login(@Valid dto: LoginDTO): LoginResponse

    @PATCH
    @Path("/reset-password")
    @PermitAll
    fun resetPassword(@Valid dto: UserForgetPasswordDTO): UserResponse

    @PATCH
    @Path("/update-password")
    fun updatePassword(@Valid dto: UserChangePasswordDTO): UserResponse

    @POST
    @Path("logout")
    fun logout()

    @POST
    @Path("/refresh-token")
    fun refreshToken(@Valid dto:RefreshTokenRequest): ApiResponse<UserDTO>
}