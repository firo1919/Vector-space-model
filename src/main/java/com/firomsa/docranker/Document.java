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
    private Map<String, Integer> file;
    private int size;
    private String name;
    private BufferedReader reader;

    public Document(File text) {
        this.name = text.getName();
        this.file = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader(text));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR OCCURED: >>>> "+e.getMessage());
        }
        int i = 0;
        try {
            // Performing word tokenization with frequency of terms
            while(reader.ready()){
                System.out.println("READING ...");
                String[] terms = reader.readLine().split(" ");
                for (String term : terms) {
                    term = term.toLowerCase();
                    term = term.replace('.', ' ');
                    term = term.trim();
                    // performing stop word removal
                    if(!term.isEmpty()&&(!TextProcesser.isStopWord(term))){
                        file.put(term, file.getOrDefault(term,0)+1);
                        i++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR OCCURED: >>>> "+e.getMessage());
        }
        this.size = i;
    }
    
    public Document(String text) {
        this.name = "The Query";
        this.file = new HashMap<>();  
        String[] terms = text.split(" ");
        int j = 0;
        for (int i = 0; i < terms.length; i++) {
            terms[i] = terms[i].toLowerCase();
            if(!TextProcesser.isStopWord(terms[i])){
                file.put(terms[i], file.getOrDefault(terms[i],0)+1);
                j++;
            }
        }
        this.size = j;
    }
    public Set<String> getTerms(){
        return this.file.keySet();
    }
    
    public int getSize() {
        return this.size;
    }
    public void setSize(int size){
        this.size = size;
    }

    public String getName() {
        return name;
    }
    
    public Map<String, Integer> getFile() {
        return file;
    }
    public void removeTerm(String key){
        this.file.remove(key);
    }

    public void addTerm(String key, int value){
        this.file.put(key, this.file.getOrDefault(key, 1));
    }

    public int getTermFrequency(String term){
        if(file.get(term)!=null){
            return file.get(term);
        }
        return 0;
    }

    public void doTextOperation(){
        // performing stemming
        TextProcesser.stemText(this);
    }
    @Override
    public String toString() {
        return "Document Name: %s ------------ Document size: %d  ".formatted(this.getName(),this.getSize());
    }

    public void clearTerms() {
        this.file.clear();
    }
    
    
    

}
