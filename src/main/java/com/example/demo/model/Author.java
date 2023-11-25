package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "AutorName")
    private String authorName = null;
    @Column(name = "AuthorSurname")
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
        return this.getAuthorName() + " " + this.getAuthorSurname();
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
