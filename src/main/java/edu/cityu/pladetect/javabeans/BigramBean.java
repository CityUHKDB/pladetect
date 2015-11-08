package edu.cityu.pladetect.javabeans;

import java.io.Serializable;

public class BigramBean implements Serializable {
    private String first_word;
    private String second_word;

    public void setFirst_word(String first_word) {
        this.first_word = first_word;
    }

    public void setSecond_word(String second_word) {
        this.second_word = second_word;
    }

    public String getBigramSQLString() {
        return "INSERT INTO word(author_id, doc_id, para_id, sen_id, first_word, second_word) " +
                "VALUES (currval('author_author_id_seq'), currval('document_doc_id_seq'), " +
                "currval('paragraph_para_id_seq'), currval('sentence_sen_id_seq'), '" + first_word + "', '" + second_word + "');";
    }
}
