package org.thoughts.on.java.model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Author
 *
 */
@Entity

public class Author implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	
	private String name;

	@OneToMany(mappedBy="author")
	private List<Book> books = new ArrayList<Book>();

	public Author() {
		super();
	}   
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
}
