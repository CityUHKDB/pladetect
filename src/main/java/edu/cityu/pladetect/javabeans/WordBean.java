package edu.cityu.pladetect.javabeans;

import java.io.Serializable;

public class WordBean implements Serializable {
    private String word;
    private int word_position;
    private String word_POS; // pos stands for part-of-speech

    public void setWord(String word) {
        this.word = word;
    }

    public void setWord_position(int word_position) {
        this.word_position = word_position;
    }

    public void setWordPartOfSpeect(String word_pos) {
        this.word_POS = word_pos;
    }

    public String getWordSQLString() {
        return "INSERT INTO word(author_id, doc_id, para_id, sen_id, word, word_position, word_POS) " +
                "VALUES (currval('author_author_id_seq'), currval('document_doc_id_seq'), " +
                "currval('paragraph_para_id_seq'), currval('sentence_sen_id_seq'), '" + word + "' + " + word_position + ", '" + word_POS + "')";
    }
}
