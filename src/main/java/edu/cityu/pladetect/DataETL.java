package edu.cityu.pladetect;

import edu.cityu.pladetect.javabeans.*;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.*;
import java.util.*;

public class DataETL {
    private static final DataETL instance;

    static {
        try {
            instance = new DataETL();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private String path;
    private InputStream isSenModel;
    private SentenceModel sentenceModel;
    private SentenceDetectorME detectorME;
    private InputStream isTokenModel;
    private TokenizerModel tokenizerModel;
    private TokenizerME tokenizerME;

    // Java Beans to be used to insert data into tables
    private ArrayList<SentenceBean> senBeanList;
    private ArrayList<PunctuationBean> puncBeanList;
    private ArrayList<WordBean> wordBeanList;
    private ArrayList<BigramBean> bigramBeanList;

    private DataETL() throws IOException {
        this.path = "";
        this.isSenModel = new FileInputStream("/home/dickson/Dropbox/City U/FYP_Planing/PlaDetect/web/OpenNLPlib/en-sent.bin");
        this.sentenceModel = new SentenceModel(isSenModel);
        this.detectorME = new SentenceDetectorME(sentenceModel);
        this.isTokenModel = new FileInputStream("/home/dickson/Dropbox/City U/FYP_Planing/PlaDetect/web/OpenNLPlib/en-token.bin");
        this.tokenizerModel = new TokenizerModel(isTokenModel);
        this.tokenizerME = new TokenizerME(tokenizerModel);

        // initialize ArrayList
        senBeanList = new ArrayList<SentenceBean>();
        puncBeanList = new ArrayList<PunctuationBean>();
        wordBeanList = new ArrayList<WordBean>();
        bigramBeanList = new ArrayList<BigramBean>();
    }

    public static DataETL getInstance() {
        return instance;
    }

    public void initialize(String path) {
        this.path = path;
        ArrayListcleanUp();
    }

    public void cleanUp() {
        this.path = "";
        ArrayListcleanUp();
    }

    private void ArrayListcleanUp() {
        senBeanList.clear();
        puncBeanList.clear();
        wordBeanList.clear();
        bigramBeanList.clear();
    }

    public String getExtractedStylometricFeatureQueries() throws Exception {
        Scanner scn = new Scanner(new File(path));
        String line, paragraph, SQL_INSERT_QUERIES;
        paragraph = SQL_INSERT_QUERIES = "";
        int para_total = 0;
        boolean isStart = true;
        Map<Integer, Integer> parainfo;
        ParagraphBean pb;

        while (scn.hasNext()) {
            if ((line = scn.nextLine()).isEmpty()) {
                // an empty line implies the program has reached the end of a paragraph
                para_total++;
                pb = new ParagraphBean();

                parainfo = splitToSentence(para_total, paragraph);
                Map.Entry<Integer, Integer> entry = parainfo.entrySet().iterator().next();

                pb.setNo_of_sen(entry.getKey());
                pb.setNo_of_word(entry.getValue());

                SQL_INSERT_QUERIES += pb.getParagraphSQLString() + "\n" + getSQLInsertQueriesForAParagraph();

                paragraph = "";
                isStart = true;
            } else {
                paragraph += (isStart) ? line : " " + line;
                isStart = false;
            }
        }

        scn.close();
        return SQL_INSERT_QUERIES;
    }

    private HashMap<Integer, Integer> splitToSentence(int paragraphNo, String paragraph) throws IOException {
        int numberOfWords, numberOfWordsPerSen, numberOfSentence;
        numberOfWords = numberOfWordsPerSen = numberOfSentence = 0;
        HashMap<Integer, Integer> paraInfo = new HashMap<Integer, Integer>();
        String[] sentences = detectorME.sentDetect(paragraph);
        SentenceBean sb;

        for (String s : sentences) {
            numberOfSentence++;
            numberOfWordsPerSen = splitToToken(s);
            numberOfWords += numberOfWordsPerSen;

            sb = new SentenceBean();
            sb.setNo_of_word(numberOfWordsPerSen);
            senBeanList.add(sb);
        }
        paraInfo.put(numberOfSentence, numberOfWords);
        return paraInfo;
    }

    private int splitToToken(String sentence) {
        String previous, word;
        previous = "";
        String[] tokens = tokenizerME.tokenize(sentence);
        //HashMap<String, Integer> bigramCount = new HashMap<String, Integer>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].matches("[^a-zA-Z\\d\\w]")) {
                saveToPunctuationBean(tokens[i], i + 1);
            } else {
                word = tokens[i].toLowerCase();
                saveToWordBean(word, i + 1);
                if (previous.isEmpty()) {
                    previous = word;
                    continue;
                }

//                bigram = previous + "-" + tokens[i];
//                if (!bigramCount.containsKey(bigram))
//                    bigramCount.put(bigram, 1);
//                else
//                    bigramCount.put(bigram, (Integer) bigramCount.get(bigram) + 1);
                saveToBigramBean(previous, word);
                previous = word;
            }
        }

        //saveToBigramBean(bigramCount);
        return tokens.length;
    }

    private void saveToPunctuationBean(String punc, int postion) {
        PunctuationBean puB = new PunctuationBean();
        puB.setPunc_mark(punc);
        puB.setPosition(postion);
        puncBeanList.add(puB);
    }

    private void saveToWordBean(String word, int position) {
        WordBean wb = new WordBean();
        wb.setWord(word);
        wb.setWord_position(position);
        // functions to be added to detect part of speech
        wb.setWordPartOfSpeect("TT");
        wordBeanList.add(wb);
    }

    private void saveToBigramBean(String first_word, String second_word) //HashMap<String, Integer> hm)
    {
        BigramBean bb;
//        String key;
//        Iterator it = hm.entrySet().iterator();
//        while (it.hasNext()){
//            Map.Entry bigram = (Map.Entry) it.next();
//            bb = new BigramBean();
//            key = bigram.getKey().toString();
//            bb.setFirst_word(key.split("-")[0]);
//            bb.setSecond_word(key.split("-")[1]);
//            bb.setFrequency(Integer.parseInt(bigram.getValue().toString()));
//            bigramBeanList.add(bb);
//        }
        bb = new BigramBean();
        bb.setFirst_word(first_word);
        bb.setSecond_word(second_word);
        bigramBeanList.add(bb);
    }

    private String getSQLInsertQueriesForAParagraph()
    {
        String SQL_QUERIES_FOR_PARA = "";

        for (SentenceBean sb : senBeanList) {
            SQL_QUERIES_FOR_PARA += sb.getSentenceSQLString() + "\n";
            for (PunctuationBean pb : puncBeanList)
                SQL_QUERIES_FOR_PARA += pb.getPuncSQLString() + "\n";
            for (WordBean wb : wordBeanList)
                SQL_QUERIES_FOR_PARA += wb.getWordSQLString() + "\n";
            for (BigramBean bb : bigramBeanList)
                SQL_QUERIES_FOR_PARA += bb.getBigramSQLString() + "\n";
        }

        ArrayListcleanUp();
        return SQL_QUERIES_FOR_PARA;
    }
}
