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
       File file = new File("/home/firomsa/Downloads/AI.txt");
       Document doc = new Document(file);
       System.out.println("  before stemming");
       System.out.println(doc);
       System.out.println("after textoperation");
       doc.doTextOperation();
       System.out.println(doc);

    }
}
