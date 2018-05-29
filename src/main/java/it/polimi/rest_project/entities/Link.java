package it.polimi.rest_project.entities;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

public class Link implements Serializable {

	private static final long serialVersionUID = -8292004600551619853L;
	private URI href;
	private String rel;

	public Link() {
	}

	public Link(String href, String rel) {
		try {
			this.href = new URI(href);
			this.rel = rel;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public URI getHref() {
		return href;
	}

	public String getRel() {
		return rel;
	}

	public void setHref(URI href) {
		this.href = href;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

}
