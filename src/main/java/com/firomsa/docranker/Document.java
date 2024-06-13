package com.firomsa.docranker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        try {
            while(reader.ready()){
                System.out.println("READING ...");
                String[] terms = reader.readLine().split(" ");
                for (String term : terms) {
                    term.toLowerCase();
                    file.put(term, file.getOrDefault(term,0)+1);
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR OCCURED: >>>> "+e.getMessage());
        }
        this.size = file.size();
    }
    
    
    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTermFrequency(String term){
        if(file.get(term)!=null){
            return file.get(term);
        }
        return 0;
    }

    @Override
    public String toString() {
        return """
        Document Name: %s
        Document size: %d
        >>>>>>>>>>>>>>  Terms   >>>>>>>>>>>>>>
        %s   
        """.formatted(this.getName(),this.getSize(),this.file);
    }
    
    
    

}
