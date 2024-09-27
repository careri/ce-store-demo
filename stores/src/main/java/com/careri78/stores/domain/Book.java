package com.careri78.stores.domain;

import org.apache.commons.lang3.StringUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public class Book {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "title")
	private String title;

    public Book() {
    }

    public Book(String title) {
        this();
        setTitle(title);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws IllegalArgumentException{
        if (!StringUtils.isNoneBlank(title)) {
            throw new IllegalArgumentException("Title can't be empty");
        }

        this.title = title;
    }

    public void setId(long id) {
        this.id = id;
    }
}
