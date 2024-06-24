package com.firomsa.docranker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TermWeightMatrix {
    private List<Document> documents;
    private int collectionNumber;
    private Map<String, Integer> terms;
    private Document target;

    public TermWeightMatrix(Document target,List<Document> documents){

        // documents from the corpus including target
        this.documents = documents;

        // the targeted query
        this.target = target;
        this.documents.add(target);

        // total document number
        this.collectionNumber = this.documents.size();

        // map of terms with document frequency
        terms = new LinkedHashMap<>();
        for (Document document : this.documents) {
            for (String term : document.getTerms()) {
                terms.put(term, terms.getOrDefault(term, 0)+1);
            }
        }
    }

    // calculates the IDF() of a term 
    private double calculateIDF(String term){
        int documentFrequency =  terms.get(term);
        // we add 1 to the IDF after logarithmic calculation to give it a lower bound to avoid 
        // zero weight just because a word appears in every document
        return Math.round((Math.log10(collectionNumber/documentFrequency)+1)*10000.0)/10000.0;
    }

    // calculates the TF_IDF of a term in a given document
    private double calculateTF_IDF(String term, Document doc){
        double tfIdf = doc.getTermFrequency(term)*calculateIDF(term);
        return Math.round(tfIdf*10000.0)/10000.0;
    }

    //calculates the cosine similarity of each document with the target document
    public Map<Document,Double> calculateCosineSimilarity(){
        Map<Document, List<Double>> docVector = new HashMap<>();

        // calculating the TFIDF for each term in a document
        for (Document item : documents) {
            List<Double> tfIdf = new ArrayList<>();
            for (String term : terms.keySet()) {
                double weight = calculateTF_IDF(term,item);
                tfIdf.add(weight);
            }
            docVector.put(item, tfIdf);
        }
        // calculating the cosine similarity for each document
        Map<Document, Double> result = new HashMap<>();
        double targetMag = magnitude(docVector.get(target));
        List<Double> targetVector = docVector.get(target);
        for (Map.Entry<Document,List<Double>> document : docVector.entrySet()) {
            if(document.getKey() == this.target){
                continue;
            }
            double docMag = magnitude(document.getValue());
            double dotProduct = dotProduct(targetVector, document.getValue());
            double answer = Math.round((dotProduct/(targetMag*docMag))*10000.0)/10000.0;
            result.put(document.getKey(), answer);
        }
        List<Map.Entry<Document,Double>> l = new LinkedList<>(result.entrySet());
        l.sort((a,b)->(b.getValue().compareTo(a.getValue())));
        Map<Document, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Document, Double> entry : l) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private double magnitude(List<Double> list){
        double result = 0;
        for (Double elem : list) {
            result += (elem*elem);
        }
        result = Math.round((Math.sqrt(result))*1000.0)/1000.0;
        return result;
    }
    private double dotProduct(List<Double> list1,List<Double> list2){
        double result = 0;
        for (int i = 0; i < list1.size(); i++) {
            result += list1.get(i)*list2.get(i);
        }
        return Math.round((result*1000.0))/1000.0;
    }
    
}
