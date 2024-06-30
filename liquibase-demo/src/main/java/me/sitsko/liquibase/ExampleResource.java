package me.sitsko.liquibase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/liquibase")
@ApplicationScoped
public class ExampleResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Hello from Quarkus REST";
	}
}
