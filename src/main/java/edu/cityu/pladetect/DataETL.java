package edu.cityu.pladetect;

import edu.cityu.pladetect.javabeans.*;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
    private PrintWriter out;
    private InputStream isSenModel;
    private SentenceModel sentenceModel;
    private SentenceDetectorME detectorME;
    private InputStream isTokenModel;
    private TokenizerModel tokenizerModel;
    private TokenizerME tokenizerME;

    // Java Beans to be used to insert data into tables
    private ArrayList<ParagraphBean> paraBeanList;
    private ArrayList<ChapterBean> chapBeanList;
    private ArrayList<SentenceBean> senBeanList;
    private ArrayList<PunctuationBean> puncBeanList;
    private ArrayList<WordBean> wordBeanList;
    private ArrayList<BigramBean> bigramBeanList;

    private DataETL() throws IOException {
        this.path = "";
        this.out = null;
        this.isSenModel = new FileInputStream("/home/dickson/Dropbox/City U/FYP_Planing/PlaDetect/web/OpenNLPlib/en-sent.bin");
        this.sentenceModel = new SentenceModel(isSenModel);
        this.detectorME = new SentenceDetectorME(sentenceModel);
        this.isTokenModel = new FileInputStream("/home/dickson/Dropbox/City U/FYP_Planing/PlaDetect/web/OpenNLPlib/en-token.bin");
        this.tokenizerModel = new TokenizerModel(isTokenModel);
        this.tokenizerME = new TokenizerME(tokenizerModel);

        // initialize ArrayList
        paraBeanList = new ArrayList<ParagraphBean>();
        chapBeanList = new ArrayList<ChapterBean>();
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
        paraBeanList.clear();
        chapBeanList.clear();
        senBeanList.clear();
        puncBeanList.clear();
        wordBeanList.clear();
        bigramBeanList.clear();
    }

    public void readFile() throws Exception {
        Scanner scn = new Scanner(new File(path));
        out.println();

        String line, paragraph;
        paragraph = "";
        int para_total = 0;
        boolean isStart = true;
        Map<Integer, Integer> parainfo;
        ParagraphBean pb;

        while (scn.hasNext()) {
            if ((line = scn.nextLine()).isEmpty()) {
                // an empty line implies the program has reached the end of a paragraph
                para_total++;
                out.println("\n\nNow reach: " + para_total + " paragraph\n");
                pb = new ParagraphBean();

                parainfo = splitToSentence(para_total, paragraph);
                Map.Entry<Integer, Integer> entry = parainfo.entrySet().iterator().next();

                pb.setNo_of_sen(entry.getKey());
                pb.setNo_of_word(entry.getValue());
                paraBeanList.add(pb);

                paragraph = "";
                isStart = true;
            } else {
                paragraph += (isStart) ? line : " " + line;
                isStart = false;
            }
        }

        scn.close();
    }

    private HashMap<Integer, Integer> splitToSentence(int paragraphNo, String paragraph) throws IOException {
        int numberOfWords, numberOfWordsPerSen, numberOfSentence;
        numberOfWords = numberOfWordsPerSen = numberOfSentence = 0;
        HashMap<Integer, Integer> paraInfo = new HashMap<Integer, Integer>();
        String[] sentences = detectorME.sentDetect(paragraph);
        SentenceBean sb;

        for (String s : sentences) {
            numberOfSentence++;
            out.println(s);
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
        String previous = "";
        String[] tokens = tokenizerME.tokenize(sentence);
        ArrayList<String> puncList = new ArrayList<String>();
        PunctuationBean puB;
        WordBean wb;
        BigramBean bb;

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].matches("[^a-zA-Z\\d\\w]")) {
                puB = new PunctuationBean();
                puB.setPunc_mark(tokens[i]);
                puB.setPosition(i + 1);
                puncList.add(tokens[i]);
            } else {
                wb = new WordBean();
                wb.setWord(tokens[i]);
                wb.setWord_position(i + 1);
                // functions to be added to detect part of speech
                wb.setWordPartOfSpeect("TT");
                wordBeanList.add(wb);

                if (previous.isEmpty()) {
                    previous = tokens[i];
                    continue;
                }

                bb = new BigramBean();
                bb.setFirst_word(previous);
                bb.setSecond_word(tokens[i]);
                bigramBeanList.add(bb);

                out.println(previous + " - " + tokens[i]);
                previous = tokens[i];
            }
        }

        out.print("Match special case in previous sentence: ");
        for (String s : puncList) {
            out.print(s + " ");
        }
        out.println("\n");
        return tokens.length;
    }

    public String InsertDataToDB()
    {
        String SQL_INSERT_QUERIES;
        for (ParagraphBean pb: paraBeanList){
            pb.getParagraphSQLString();
        }
        return null;
    }
}
