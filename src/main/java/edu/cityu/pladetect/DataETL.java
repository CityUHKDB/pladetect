package edu.cityu.pladetect;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.*;
import java.util.ArrayList;
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

    private DataETL() throws IOException {
        this.path = "";
        this.out = null;
        this.isSenModel = new FileInputStream("/home/dickson/Dropbox/City U/FYP_Planing/PlaDetect/web/OpenNLPlib/en-sent.bin");
        this.sentenceModel = new SentenceModel(isSenModel);
        this.detectorME = new SentenceDetectorME(sentenceModel);
        this.isTokenModel = new FileInputStream("/home/dickson/Dropbox/City U/FYP_Planing/PlaDetect/web/OpenNLPlib/en-token.bin");
        this.tokenizerModel = new TokenizerModel(isTokenModel);
        this.tokenizerME = new TokenizerME(tokenizerModel);
    }

    public static DataETL getInstance() {
        return instance;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public void disposePath() {
        this.path = "";
    }

    public void disposeWriter() {
        this.out = null;
    }

    public void readFile() throws Exception {
        Scanner scn = new Scanner(new File(path));
        out.println();

        String line, paragraph;
        paragraph = "";
        int para_total = 0;
        boolean isStart = true;

        while (scn.hasNext()) {
            if ((line = scn.nextLine()).isEmpty()) {
                para_total++;
                out.println("\n\nNow reach: " + para_total + " paragraph\n");
                splitToSentence(para_total, paragraph);
                paragraph = "";
                isStart = true;
            } else {
                paragraph += (isStart) ? line : " " + line;
                isStart = false;
            }
        }

        scn.close();
    }

    private void splitToSentence(int paragraphNo, String paragraph) throws IOException {
        int sentenceId = 0;
        String[] sentences = detectorME.sentDetect(paragraph);
        for (String s : sentences) {
            sentenceId++;
            out.println(s);
            splitToToken(paragraphNo, sentenceId, s);
        }
    }

    private void splitToToken(int paragraphNo, int sentenceId, String sentence) {
        String previous = "";
        String[] tokens = tokenizerME.tokenize(sentence);
        ArrayList<String> puncList = new ArrayList<String>();
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].matches("[^a-zA-Z\\d\\w]")) {
                puncList.add(tokens[i]);
            } else {
                if (previous.isEmpty()) {
                    previous = tokens[i];
                    continue;
                }

                out.println(previous + " - " + tokens[i]);
                previous = tokens[i];
            }
        }

        out.print("Match special case in previous sentence: ");
        for (String s : puncList) {
            out.print(s + " ");
        }
        out.println("\n");
    }
}
