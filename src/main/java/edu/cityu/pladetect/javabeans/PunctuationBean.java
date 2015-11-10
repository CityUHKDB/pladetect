package edu.cityu.pladetect.javabeans;

import java.io.Serializable;

public class PunctuationBean implements Serializable {
    private int position;
    private String punc_mark;

    public void setPosition(int position) {
        this.position = position;
    }

    public void setPunc_mark(String punc_mark) {
        this.punc_mark = punc_mark;
    }

    public String getPuncSQLString() {
        return "INSERT INTO punctuation(author_id, doc_id, para_id, sen_id, punc_mark, punc_position) " +
                "VALUES (currval('author_author_id_seq'), currval('document_doc_id_seq'), " +
                "currval('paragraph_para_id_seq'), currval('sentence_sen_id_seq'), '" + punc_mark + "', " + position + ")";
    }
}
