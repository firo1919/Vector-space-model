package com.firomsa.docranker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TermWeightMatrix {
    private Map<Document,List<Integer>> docVector;
    private int collectionNumber;
    HashMap<String, Integer> terms;
    private Document target;
    public TermWeightMatrix(Document target,Document ...documents){
        docVector = new LinkedHashMap<>();
        terms = new LinkedHashMap<>();
        List<Document> docs = new ArrayList<>();
        int i = 0;
        for (Document document : documents) {
            docs.add(document);
            for (String term : document.getTerms()) {
                terms.put(term, terms.getOrDefault(term, 0)+1);
            }
            i++;
        }
        docs.add(target);
        for (String term : target.getTerms()) {
            terms.put(term, terms.getOrDefault(term, 0)+1);
        }
        for (Document doc : docs) {
            List<Integer> termfreqList = new ArrayList<>();
            for (String term : terms.keySet()) {
                int termFreq = doc.getTermFrequency(term);
                termfreqList.add(termFreq);
            }
            docVector.put(doc, termfreqList);
        }
        this.target = target;
        this.collectionNumber = i+1;
    }

    public double calculateIDF(String term){
        int numberOfAppearances =  terms.get(term);
        return Math.log10(collectionNumber/numberOfAppearances);
    }

    public double calculateTF_IDF(String term, Document doc){
        double tfIdf = doc.getTermFrequency(term)*calculateIDF(term);
        return tfIdf;
    }
    public ArrayList<Double> calculateSimilarity(){

    }

    public double cosineSimilarity(Document doc1, Document doc2){

    }
    @Override
    public String toString() {
        return "TermWeightMatrix [termWithDocs=" + termWithDocs + "]";
    }
    
}
