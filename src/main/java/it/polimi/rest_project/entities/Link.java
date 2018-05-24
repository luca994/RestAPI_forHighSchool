package it.polimi.rest_project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Links")
public class Link {

	@Id
	private String uri;
	@Column
	private String rel;

	public Link() {}
	
	public Link(String uri, String rel) {
		this.uri=uri;
		this.rel=rel;
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
