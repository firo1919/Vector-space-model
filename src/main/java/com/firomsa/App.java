package com.firomsa;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.firomsa.docranker.Document;
import com.firomsa.docranker.TermWeightMatrix;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       Document doc1 = new Document(new File("/home/firomsa/Downloads/AI.txt"));
       Document doc2 = new Document(new File("/home/firomsa/Downloads/mentalhealth.txt"));
       Document doc3 = new Document(new File("/home/firomsa/Downloads/opensource.txt"));
       Document doc4 = new Document(new File("/home/firomsa/Downloads/Renewable.txt"));
       Document doc5 = new Document("Artificial intelligence and machine learning");
       TermWeightMatrix matrix = new TermWeightMatrix(doc5, new ArrayList<Document>(Arrays.asList(doc1,doc2,doc3,doc4)));
       System.out.println(matrix.calculateCosineSimilarity());
    }
}
