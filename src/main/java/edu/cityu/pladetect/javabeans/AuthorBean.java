package edu.cityu.pladetect.javabeans;

import java.io.Serializable;

public class AuthorBean implements Serializable {
    private String author_name;
    private String author_type;

    public AuthorBean() {
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public void setAuthor_type(String author_type) {
        this.author_type = author_type;
    }

    public String getAuthorSQLString() {
        return "INSERT INTO author(author_name, author_type) VALUES ('" + author_name + "', '" + author_type + "');";
    }
}
