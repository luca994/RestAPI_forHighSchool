package it.polimi.rest_project.entities;

import java.io.Serializable;

public class Link implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1762280567508862221L;
	private String uri;
	private String rel;

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @return the rel
	 */
	public String getRel() {
		return rel;
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @param rel
	 *            the rel to set
	 */
	public void setRel(String rel) {
		this.rel = rel;
	}

}
