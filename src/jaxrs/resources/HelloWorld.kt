package jaxrs.resources

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("helloWorld")
class HelloWorldResource {
    @GET
    fun helloWorld() = "Hello World"

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
    data class HelloJson(val intProp1: Int, val stringProp2: String)

    @GET
    @Path("json")
    @Produces(MediaType.APPLICATION_JSON)
    fun helloJson() = HelloJson(1, "test")

    @POST
    @Path("json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun postInfo(input: HelloJson): HelloJson {
        return HelloJson(input.intProp1+1, "Hello "+input.stringProp2)
    }
}
