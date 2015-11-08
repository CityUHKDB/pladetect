package edu.cityu.pladetect.javabeans;

import java.io.Serializable;

public class DocumentBean implements Serializable {
    private String doc_title;
    private String doc_type;
    private String year_of_pub;

    public void setDoc_title(String doc_title) {
        this.doc_title = doc_title;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public void setYear_of_pub(String year_of_pub) {
        this.year_of_pub = year_of_pub;
    }

    public String getDocSQLString() {
        // Still need to escape character =]
        return "INSERT INTO document(author_id, doc_title, doc_type, year_of_pub) " +
                "VALUES(currval('author_author_id_seq'), '" + doc_title + "', '" + doc_title + "', '" + year_of_pub + "');";
    }
}
