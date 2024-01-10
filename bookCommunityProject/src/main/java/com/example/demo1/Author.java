package com.example.demo1;

//import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Getter
@Setter
@JsonAutoDetect
//@Entity
//@Table(name = "Authors")
public class Author {
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private long author_id;
    //@Column(name = "AutorName")
    private String authorName = null;
    //@Column(name = "AuthorSurname")
    private String authorSurname = null;
    Author(String aN , String aS)
    {
        this.authorName = aN;
        this.authorSurname = aS;
    }

    public Author() {}
    
    @Override
    public String toString()
    {
        return this.authorName + " " + this.getAuthorSurname();
    }
    public String getAuthorName() {
		return authorName;
	}

	public String getAuthorSurname() {
		return authorSurname;
	}
	public void setAuthorName(String name) {
		this.authorName = name;
	}

	public void setAuthorSurname(String surname) {
		this.authorSurname = surname;
	}

	@Override
    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        Author author = (Author) obj;
        return this.getAuthorName().equals(author.getAuthorName())
                && this.getAuthorSurname().equals(author.authorSurname);
    }
}