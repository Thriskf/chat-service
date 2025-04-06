package org.elteq.logic.auth.api

import io.quarkus.security.Authenticated
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement

@Authenticated
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v1/-auth")
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "SecurityScheme")

interface AuthApi {
    @POST
    @Path("/login")
    fun login()

    @PATCH
      @Path("/reset-password")
    fun resetPassword()

    @PATCH
    @Path("/update-password")
    fun updatePassword()

    @POST
    @Path("logout")
    fun logout()
}