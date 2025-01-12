package org

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
@Path("/test")
class PingResource {

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "Hello RESTEasy"

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    fun ping() = "PONG!"
}