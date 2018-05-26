package it.polimi.rest_project.entities;

import java.io.Serializable;

public class Link implements Serializable {

	private String uri;
	private String rel;

	public Link() {
	}

	public Link(String uri, String rel) {
		this.uri = uri;
		this.rel = rel;
	}

	public String getUri() {
		return uri;
	}

	public String getRel() {
		return rel;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

}
