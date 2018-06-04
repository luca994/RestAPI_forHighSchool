package it.polimi.rest_project.filters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.DatatypeConverter;

/**
 * The cache manager for the GET requests
 *
 */
public class CachingManager implements ContainerResponseFilter {

	/**
	 * Filters all the GET response and checks if there is a valid Etag in the
	 * request, in that case, it responds with a 304 (not modified) else it adds to
	 * the response a cache-control and an ETag headers
	 */
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		if (!requestContext.getRequest().getMethod().equals("GET"))
			return;
		Object requestedResource = responseContext.getEntity();
		if (requestedResource == null)
			return;
		byte[] hashedPsw;
		try {
			hashedPsw = calculateEtag(requestedResource);
			String hex = DatatypeConverter.printHexBinary(hashedPsw);
			EntityTag etag = new EntityTag(hex);
			CacheControl cc = new CacheControl();
			cc.setMaxAge(3600);
			ResponseBuilder builder = requestContext.getRequest().evaluatePreconditions(etag);
			if (builder != null) {
				builder.cacheControl(cc);
				updateResponseContextContents(responseContext, builder);
			} else {
				responseContext.getHeaders().add("Cache-Control", "max-age=3600");
				responseContext.getHeaders().add("ETag", etag.toString());
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Computes the ETag of the object by computing the MD5 hash of the object
	 * 
	 * @param requestedResource
	 *            the resource of which the hash has to be computed
	 * @return the Etag of the resource
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	private byte[] calculateEtag(Object requestedResource) throws IOException, NoSuchAlgorithmException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		oos = new ObjectOutputStream(bos);
		oos.writeObject(requestedResource);
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] hashedPsw = md.digest(bos.toByteArray());
		return hashedPsw;
	}

	/**
	 * updates the response context with the response context taken as input
	 * 
	 * @param responseContext
	 * @param responseBuilder
	 */
	private static void updateResponseContextContents(ContainerResponseContext responseContext,
			Response.ResponseBuilder responseBuilder) {
		Response newResponse = responseBuilder.build();
		responseContext.setStatusInfo(newResponse.getStatusInfo());
		responseContext.getHeaders().clear();
		newResponse.getHeaders().forEach((key, valueList) -> responseContext.getHeaders().addAll(key, valueList));
		responseContext.setEntity(newResponse.getEntity());
	}

}
