package com.firomsa;

import java.io.File;

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
       //File file = new File("/home/firomsa/Downloads/AI.txt");
       String text = "my name is firomsa";
       Document doc = new Document(text);
       System.out.println(new TermWeightMatrix(doc));

    }
}
