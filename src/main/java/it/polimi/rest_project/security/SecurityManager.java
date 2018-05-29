package it.polimi.rest_project.security;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import it.polimi.rest_project.application.Back2School;
import it.polimi.rest_project.services.UserService;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityManager implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		UserService userService = new UserService();
		List<String> headers = requestContext.getHeaders().get(Back2School.AUTHORIZATION_HEADER_KEY);
		try {
			String auth = headers.get(0);
			auth = auth.replaceFirst(Back2School.AUTHORIZATION_HEADER_PREFIX, "");
			auth = Base64.decodeAsString(auth);
			StringTokenizer tokenizer = new StringTokenizer(auth, ":");
			String userId = tokenizer.nextToken();
			String password = tokenizer.nextToken();
			if (userService.verifyLogin(userId, password))
				return;
			else
				requestContext
						.abortWith(Response.status(Status.UNAUTHORIZED).entity("Incorrect id or password").build());
		} catch (NullPointerException e) {
			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity("You have to log in").build());
		}
	}
}
