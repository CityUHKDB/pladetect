-- create table author
CREATE TABLE author (
	author_id SERIAL PRIMARY KEY NOT NULL,
	author_name VARCHAR(40) NOT NULL,
	author_type VARCHAR(40)
);

-- create table document
CREATE TABLE document (
	doc_id SERIAL PRIMARY KEY NOT NULL,
	author_id INTEGER,
	doc_title VARCHAR(40) NOT NULL,
	doc_type VARCHAR(40) NOT NULL,
	year_of_pub DATE,
	FOREIGN KEY(author_id) REFERENCES author(author_id)
);

-- create table paragraph
CREATE TABLE paragraph (
	para_id SERIAL PRIMARY KEY NOT NULL,
	author_id INTEGER,
	doc_id INTEGER,
	no_of_sen INTEGER NOT NULL,
	no_of_word INTEGER NOT NULL,
	FOREIGN KEY(author_id) REFERENCES author(author_id),
	FOREIGN KEY(doc_id) REFERENCES document(doc_id)
);

--------------------------------------------------------------------
-- create table chapter
CREATE TABLE chapter (
	chapter_id SERIAL PRIMARY KEY NOT NULL,
	author_id INTEGER,
	doc_id INTEGER,
 	para_id INTEGER,
	chapter_no INTEGER NOT NULL,
	FOREIGN KEY(author_id) REFERENCES author(author_id),
	FOREIGN KEY(doc_id) REFERENCES document(doc_id),
	FOREIGN KEY(para_id) REFERENCES paragraph(para_id)
);
-- storing the chapter no is always optional
--------------------------------------------------------------------

-- create table sentence
CREATE TABLE sentence (
	sen_id SERIAL PRIMARY KEY NOT NULL,
	author_id INTEGER,
	doc_id INTEGER,
	para_id INTEGER,
	no_of_word INTEGER NOT NULL,
	FOREIGN KEY(author_id) REFERENCES author(author_id),
	FOREIGN KEY(doc_id) REFERENCES document(doc_id),
	FOREIGN KEY(para_id) REFERENCES paragraph(para_id)
);

-- create table punctuation
CREATE TABLE punctuation (
	punc_id SERIAL PRIMARY KEY NOT NULL,
	author_id INTEGER,
	doc_id INTEGER,
	para_id INTEGER,
	sen_id INTEGER,
	punc_mark VARCHAR(1) NOT NULL,
	freq INTEGER NOT NULL,
	FOREIGN KEY(author_id) REFERENCES author(author_id),
	FOREIGN KEY(doc_id) REFERENCES document(doc_id),
	FOREIGN KEY(para_id) REFERENCES paragraph(para_id),
	FOREIGN KEY(sen_id) REFERENCES sentence(sen_id)
);

-- create table word
CREATE TABLE word (
  word_id SERIAL PRIMARY KEY NOT NULL,
  author_id INTEGER,
  doc_id INTEGER,
	para_id INTEGER,
	sen_id INTEGER,
	word VARCHAR(40) NOT NULL,
	word_position INTEGER NOT NULL,
	word_POS VARCHAR(2) NOT NULL,
	FOREIGN KEY(author_id) REFERENCES author(author_id),
	FOREIGN KEY(doc_id) REFERENCES document(doc_id),
	FOREIGN KEY(para_id) REFERENCES paragraph(para_id),
	FOREIGN KEY(sen_id) REFERENCES sentence(sen_id)

);

-- create table bigram
CREATE TABLE bigram (
	bigram_id SERIAL PRIMARY KEY NOT NULL,
	author_id INTEGER,
	doc_id INTEGER,
	para_id INTEGER,
	sen_id INTEGER,
	first_word VARCHAR(40) NOT NULL,
	second_word VARCHAR(40) NOT NULL,
	FOREIGN KEY(author_id) REFERENCES author(author_id),
	FOREIGN KEY(doc_id) REFERENCES document(doc_id),
	FOREIGN KEY(para_id) REFERENCES paragraph(para_id),
	FOREIGN KEY(sen_id) REFERENCES sentence(sen_id)
);