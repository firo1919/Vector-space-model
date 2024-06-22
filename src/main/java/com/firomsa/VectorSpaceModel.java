package com.firomsa;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.firomsa.docranker.Document;
import com.firomsa.docranker.TermWeightMatrix;

public class VectorSpaceModel extends JFrame{
    private static TermWeightMatrix termWeightMatrix;


    public VectorSpaceModel(){
        setTitle("Document Ranker");
        setSize(1000, 600);
    }
    public static void main(String[] args) {
        Document doc1 = new Document(new File("/home/firomsa/Downloads/AI.txt"));
        Document doc2 = new Document(new File("/home/firomsa/Downloads/opensource.txt"));
        Document doc3 = new Document(new File("/home/firomsa/Downloads/mentalhealth.txt"));
        Document doc4 = new Document(new File("/home/firomsa/Downloads/Renewable.txt"));
        Document doc5 = new Document("artificial intelligence and machine learning");
        termWeightMatrix = new TermWeightMatrix(doc5, new ArrayList<>(Arrays.asList(doc1,doc2,doc3,doc4)));
        System.out.println(termWeightMatrix.calculateCosineSimilarity());
        
    }
    
}
