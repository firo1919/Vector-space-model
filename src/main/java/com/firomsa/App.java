package com.firomsa;

import java.io.File;

import com.firomsa.docranker.Document;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        File  file = new File("/home/firomsa/Documents/new.txt");
        Document doc = new Document(file);
        System.out.println(doc.getTermFrequency("had"));
    }
}
