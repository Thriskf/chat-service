package org.elteq.logic.activityLog.api

import io.quarkus.security.Authenticated
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.logic.activityLog.models.ActivityLogDTO
import org.elteq.logic.activityLog.spec.ActivityLogSpec
import java.util.*

@Path("/api/v1/activity-log")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "SecurityScheme")
@Authenticated
@Tag(name = "ActivityLog", description = "ActivityLog")
interface ActivityLogApi {

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: UUID): ApiResponse<ActivityLogDTO>

    @GET
    fun search(@BeanParam spec: ActivityLogSpec): ApiResponse<List<ActivityLogDTO>>

    @GET
    @Path("/total")
    fun count(): Response


}