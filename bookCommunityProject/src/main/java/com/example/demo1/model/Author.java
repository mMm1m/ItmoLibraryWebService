package com.example.demo1;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Getter
@Setter
@JsonAutoDetect
public class Author {
    private String authorName = null;
    private String authorSurname = null;
    private List<Book> book;
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