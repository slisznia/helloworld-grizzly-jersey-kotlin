import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import jaxrs.Application
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import javax.ws.rs.core.UriBuilder
import javax.ws.rs.ext.ContextResolver

fun main(args: Array<String>) {
    val url = UriBuilder.fromUri("http://0.0.0.0/")
            .port(8080)
            .build()

    val resourceConfig = ResourceConfig.forApplication(Application())
            .register(ContextResolver<ObjectMapper> { ObjectMapper().registerModule(KotlinModule()) })

    val httpServer = GrizzlyHttpServerFactory.createHttpServer(
            url,
            resourceConfig,
            true
    )

    if (System.getenv()["SHUTDOWN_TYPE"].equals("INPUT")) {
        println("Press any key to shutdown")
        readLine()
        println("Shutting down from input")
        httpServer.shutdownNow()
    } else {
        Runtime.getRuntime().addShutdownHook(Thread {
            println("Shutting down from shutdown hook")
            httpServer.shutdownNow()
        })

        println("Press Ctrl+C to shutdown")
        Thread.currentThread().join()
    }
}
