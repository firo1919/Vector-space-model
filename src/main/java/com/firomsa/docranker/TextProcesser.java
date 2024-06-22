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
    private static PorterStemmer stemmer;
    private static Set<String> stopwords;
    private static BufferedReader reader;

    static {
        stemmer = new PorterStemmer();
        stopwords = new HashSet<>();
        File stopwordsfile = new File("src/main/java/com/firomsa/terrier-stop.txt");
        try {
            reader = new BufferedReader(new FileReader(stopwordsfile));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR OCCURED: >>>> "+e.getMessage());
        }
        try {
            // Reading stowords
            while(reader.ready()){
                System.out.println("READING STOP WORDS ...");
                String term = reader.readLine();
                stopwords.add(term);
            }
        } catch (IOException e) {
            System.out.println("ERROR OCCURED: >>>> "+e.getMessage());
        }
    }

    public static void stemText(Document doc){
        Set<String> terms = doc.getFile().keySet();
        System.out.println(">>>>>>>>>> PERFORMING STEMMING >>>>>>>>>");
        Map<String, Integer> tempMap = new HashMap<>();
        for (String term : terms) {
            int value = doc.getFile().get(term);
            String key = stemmer.stem(term);
            tempMap.put(key, value);
        }

        doc.clearTerms(); // Assuming there's a method to clear all terms
        for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
            doc.addTerm(entry.getKey(), entry.getValue());
        }
    }
    public static boolean isStopWord(String term){
        if(stopwords.contains(term)){
            return true;
        }
        return false;
    }
}
