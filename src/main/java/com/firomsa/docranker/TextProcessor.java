package com.firomsa.docranker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import opennlp.tools.stemmer.PorterStemmer;

public class TextProcessor {
    private static InputStream stopwordStream = TextProcessor.class.getClassLoader()
                                                                  .getResourceAsStream("terrier-stop.txt");
                                                                  
    private static PorterStemmer stemmer = new PorterStemmer();
    private static Set<String> stopwords = new HashSet<>();
    private static BufferedReader reader;
   
    // static initialization of stopword file on class loading
    static {
        reader = new BufferedReader(new InputStreamReader(stopwordStream));
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
        document.replaceDocument(tempMap);
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
        document.replaceDocument(tempMap);
    }

    public static void tokenizeTerms(String[] terms, Document document) {
        for (String term : terms) {
            term = term.toLowerCase();
            // removing anwanted symbols using regular expressions
            term = term.replaceAll("[.\",:)(/\\?!&;]", "");
            term = term.trim();
            if (!term.isEmpty()) {
                document.addTerm(term);
            }
        }
    }
}
