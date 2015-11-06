-- create table author
CREATE TABLE author (
	author_id SERIAL PRIMARY KEY NOT NULL,
	author_name VARCHAR(40) NOT NULL,
	author_type VARCHAR(40)
);

-- create table document
CREATE TABLE document (
	doc_id SERIAL PRIMARY KEY NOT NULL,
	author_id INTEGER REFERENCES author(author_id) NOT NULL,
	doc_title VARCHAR(40) NOT NULL,
	year_of_pub DATE
);

-- create table paragraph
CREATE TABLE paragraph (
	para_id SERIAL PRIMARY KEY NOT NULL,
	doc_id INTEGER REFERENCES document(doc_id) NOT NULL,
	no_of_sen INTEGER NOT NULL,
	no_of_word INTEGER NOT NULL
);

--------------------------------------------------------------------
-- create table chapter
CREATE TABLE chapter (
	chapter_id SERIAL PRIMARY KEY NOT NULL,
	doc_id INTEGER REFERENCES document(doc_id) NOT NULL,
 	para_id INTEGER REFERENCES paragraph(para_id) NOT NULL,
	chapter_no INTEGER NOT NULL
);
-- storing the chapter no is always optional
--------------------------------------------------------------------

-- create table sentence
CREATE TABLE sentence (
	sen_id SERIAL PRIMARY KEY NOT NULL,
	doc_id INTEGER REFERENCES document(doc_id) NOT NULL,
	para_id INTEGER REFERENCES paragraph(para_id) NOT NULL,
	no_of_word INTEGER NOT NULL
);

-- create table punctuation
CREATE TABLE punctuation (
	punc_id SERIAL PRIMARY KEY NOT NULL,
	doc_id INTEGER REFERENCES document(doc_id) NOT NULL,
	para_id INTEGER REFERENCES paragraph(para_id) NOT NULL,
	sen_id INTEGER REFERENCES sentence(sen_id) NOT NULL,
	punc_mark VARCHAR(1) NOT NULL,
	freq INTEGER NOT NULL
);

-- create table word
CREATE TABLE word (
  word_id SERIAL PRIMARY KEY NOT NULL,
  doc_id INTEGER REFERENCES document(doc_id) NOT NULL,
	para_id INTEGER REFERENCES paragraph(para_id) NOT NULL,
	sen_id INTEGER REFERENCES sentence(sen_id) NOT NULL,
	word VARCHAR(40) NOT NULL;
	word_position INTEGER NOT NULL;
	word_POS VARCHAR(2) NOT NULL;
);

-- create table bigram
CREATE TABLE bigram (
	bigram_id SERIAL PRIMARY KEY NOT NULL,
	doc_id INTEGER REFERENCES document(doc_id) NOT NULL,
	para_id INTEGER REFERENCES paragraph(para_id) NOT NULL,
	sen_id INTEGER REFERENCES sentence(sen_id) NOT NULL,
	first_word VARCHAR(40) NOT NULL,
	second_word VARCHAR(40) NOT NULL,
);