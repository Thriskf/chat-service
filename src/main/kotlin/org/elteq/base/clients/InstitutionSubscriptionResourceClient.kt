package org.elteq.base.clients

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.elteq.base.apiResponse.domain.ApiResponse

@Path("/api/v1/institution-subscriptions")
@RegisterRestClient(configKey = "institution-subscription")
interface InstitutionSubscriptionResourceClient {
    @GET
    @Path("/{id}")
//    fun get(@PathParam("wardId") wardId: Long): ApiResponse<InstitutionSubscriptionDto>
    fun get(@PathParam("id") id: Long): ApiResponse<String>
}