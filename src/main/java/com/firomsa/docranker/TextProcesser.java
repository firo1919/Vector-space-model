package com.firomsa.docranker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import opennlp.tools.stemmer.PorterStemmer;

public class TextProcesser {
    private static PorterStemmer stemmer = new PorterStemmer();
    private static Set<String> stopwords = new HashSet<>();
    private static BufferedReader reader;
    private static final String StopWordPath = "src/main/java/com/firomsa/terrier-stop.txt";

    // static initialization of stopword file on class loading
    static {
        File stopwordsfile = new File(StopWordPath);
        try {
            reader = new BufferedReader(new FileReader(stopwordsfile));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR OCCURED WHILE LOADING STOP WORDS: >>>> " + e.getMessage());
        }
        try {
            // Reading stowords
            System.out.println("READING STOP WORDS BEGAN ...");
            while (reader.ready()) {
                String term = reader.readLine();
                stopwords.add(term);
            }
            System.out.println("READING STOP WORDS ENDED ...");
        } catch (IOException e) {
            System.out.println("ERROR OCCURED DURING READING STOP WORDS: >>>> " + e.getMessage());
        }
    }

    // a method to perform stemming on terms, uses the Porte Stemming algorithm
    public static void stemText(Document document) {
        Set<String> terms = document.getFile().keySet();
        System.out.println(">>>>>>>>>> PERFORMING STEMMING - " + document.getName());
        Map<String, Integer> tempMap = new HashMap<>();
        for (String term : terms) {
            int value = document.getFile().get(term);
            String key = stemmer.stem(term);
            tempMap.put(key, value);
        }

        document.clearTerms();
        for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
            document.addTerm(entry.getKey(), entry.getValue());
        }
    }

    public static void removeStopWord(Document document) {
        Set<String> terms = document.getFile().keySet();
        System.out.println(">>>>>>>>>> PERFORMING STOP WORD REMOVAL - " + document.getName());
        Map<String, Integer> tempMap = new HashMap<>();
        for (String term : terms) {
            if (!stopwords.contains(term)) {
                tempMap.put(term, document.getFile().get(term));
            }
        }
        document.clearTerms();
        for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
            document.addTerm(entry.getKey(), entry.getValue());
        }
        document.setSize();
    }
}
