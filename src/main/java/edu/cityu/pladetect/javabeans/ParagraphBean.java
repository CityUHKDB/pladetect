package edu.cityu.pladetect.javabeans;

import java.io.Serializable;

public class ParagraphBean implements Serializable {
    private int no_of_sen;
    private int no_of_word;

    public void setNo_of_sen(int no_of_sen) {
        this.no_of_sen = no_of_sen;
    }

    public void setNo_of_word(int no_of_word) {
        this.no_of_word = no_of_word;
    }

    public String getParagraphSQLString() {
        return "INSERT INTO paragraph(author_id, doc_id, no_of_sen, no_of_word) " +
                "VALUES(currval('author_author_id_seq'), currval('document_doc_id_seq'), " + no_of_sen + ", " + no_of_word + ");";
    }
}
