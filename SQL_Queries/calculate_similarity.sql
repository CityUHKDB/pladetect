select bigram_id, author_id, doc_id, para_id, sen_id, bigram.bigram, freq from bigram inner join bigramwithfreq on bigram.bigram = bigramwithfreq.bigram;

-- Document 1, 2, 3 are written Charles Dickens
-- They are all copied from the book Christmas Carol
-- text 1 contains 991 words
-- text 2 contains 948 words
-- text 3 contains 1377 words
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 1
INTERSECT
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 2;
-- results 43 rows
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 2
INTERSECT
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 3;
-- results 57 rows
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 1
INTERSECT
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 3;
-- results 62 rows

-- Text 4 is a text written by another author
-- text 4 contains 778 words
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 1
INTERSECT
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 4;
-- results 30 rows
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 2
INTERSECT
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 4;
-- results 28 rows
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 3
INTERSECT
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 4;
-- results 35 rows

-- Text 5 is another text written by another author
-- text 5 contains 1506 words
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 1
INTERSECT
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 5;
-- results 26 rows
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 2
INTERSECT
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 5;
-- results 30 rows
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 3
INTERSECT
SELECT DISTINCT bigram FROM bigram WHERE doc_id = 5;
-- results 38 rows