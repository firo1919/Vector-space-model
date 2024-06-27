package com.firomsa.docranker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Document {
    private Map<String, Integer> file = new HashMap<>();
    private String name;
    private BufferedReader reader;

    public Document(File text) {
        this.name = text.getName();

        try {
            reader = new BufferedReader(new FileReader(text));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR OCCURED WHILE READING FILE: >>>> " + e.getMessage());
        }
        try {
            // Performing word tokenization with frequency of terms
            System.out.println("READING FILE BEGAN...");
            while (reader.ready()) {
                String[] terms = reader.readLine().split(" ");
                TextProcessor.tokenizeTerms(terms, this);
            }
            System.out.println("READING FILE ENDED ...");

        } catch (IOException e) {
            System.out.println("ERROR OCCURED DURING READING FILES: >>>> " + e.getMessage());
        }

        // Removing stop words
        TextProcessor.removeStopWord(this);
        // performing stemming
        TextProcessor.stemText(this);
    }

    public Document(String text) {
        this.name = "The Query";
        String[] terms = text.split(" ");

        // Performing word tokenization with frequency of terms processor
        TextProcessor.tokenizeTerms(terms, this);
        // removing stop words
        TextProcessor.removeStopWord(this);
        // performing stemming
        TextProcessor.stemText(this);
    }

    public Set<String> getTerms() {
        return this.file.keySet();
    }

    public int getSize() {
        return this.getFile().size();
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getFile() {
        return file;
    }

    public void addTerm(String term) {
        this.file.put(term, this.file.getOrDefault(term, 1));
    }

    public void replaceDocument(Map<String, Integer> newFile) {
        this.file.clear();
        for (Map.Entry<String, Integer> entry : newFile.entrySet()) {
            this.file.put(entry.getKey(), entry.getValue());
        }
    }

    public int getTermFrequency(String term) {
        if (file.get(term) != null) {
            return file.get(term);
        }
        return 0;
    }

    @Override
    public String toString() {
        return ("Document Name: " + "%" + (-20) + "s" + "%" + (-20) + "s  Document size: %d Unique terms  ")
                .formatted(this.getName(), "------------", this.getSize());
    }

}
