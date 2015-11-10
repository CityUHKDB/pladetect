package edu.cityu.pladetect.javabeans;

import java.io.Serializable;

public class BigramBean implements Serializable {
    private String first_word;
    private String second_word;
    private int frequency;

    public void setFirst_word(String first_word) {
        this.first_word = first_word;
    }

    public void setSecond_word(String second_word) {
        this.second_word = second_word;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getBigramSQLString() {
        return "INSERT INTO bigram(author_id, doc_id, para_id, sen_id, bigram) " +
                "VALUES (currval('author_author_id_seq'), currval('document_doc_id_seq'), " +
                "currval('paragraph_para_id_seq'), currval('sentence_sen_id_seq'), '" + first_word.replace("'", "''") + " " + second_word.replace("'", "''") + "');";
    }
}
