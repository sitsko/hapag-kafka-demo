package me.sitsko.liquibase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/api/v1/")
public class LiquibaseResource {

	@Path("liquibase")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String databaseVersion() {
		return "Liquibase demo";
	}
}
