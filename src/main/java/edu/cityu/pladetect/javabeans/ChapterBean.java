package edu.cityu.pladetect.javabeans;

import java.io.Serializable;

public class ChapterBean implements Serializable {
    private int chapter_no;

    public void setChapter_no(int chap_no) {
        this.chapter_no = chap_no;
    }

    public String getChapterSQLString() {
        return "INSERT INTO chapter(author_id, doc_id, para_id, chapter_no) " +
                "VALUES (currval('author_author_id_seq'), currval('document_doc_id_seq'), " +
                "currval('paragraph_para_id_seq'), " + chapter_no + ");";
    }
}
