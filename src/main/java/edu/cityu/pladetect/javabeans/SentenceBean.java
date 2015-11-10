package edu.cityu.pladetect.javabeans;

import java.io.Serializable;

public class SentenceBean implements Serializable {
    private int no_of_word;

    public void setNo_of_word(int no_of_word) {
        this.no_of_word = no_of_word;
    }

    public String getSentenceSQLString() {
        return "INSERT INTO sentence(author_id, doc_id, para_id, no_of_word) " +
                "VALUES (currval('author_author_id_seq'), currval('document_doc_id_seq'), " +
                "currval('paragraph_para_id_seq'), " + no_of_word + ");";
    }
}
