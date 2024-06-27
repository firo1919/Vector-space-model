package com.firomsa;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.firomsa.docranker.Document;
import com.firomsa.docranker.TermWeightMatrix;

public class VectorSpaceModel extends JFrame {
    private static TermWeightMatrix termWeightMatrix;
    private GridBagConstraints gbc = new GridBagConstraints();
    private static ArrayList<Document> documents = new ArrayList<>();
    private static Document target;

    public VectorSpaceModel() {
        JButton b1, b2, b3;
        setLayout(new GridBagLayout());
        setTitle("Document Ranker");
        setSize(1000, 600);

        b1 = new JButton("1");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        add(b1, gbc);

        b2 = new JButton("2");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        add(b2, gbc);

        b3 = new JButton("3");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        add(b3, gbc);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        // user interface not implemented yet
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            new VectorSpaceModel();
        } else {
            ArrayList<String> inputs = new ArrayList<>(Arrays.asList(args));
            if (!containsOption(inputs)) {
                System.out.println(">>>>>> WRONG SYNTAX >>>>>>>>");
                return;
            }
            for (String input : inputs) {
                if (input.equals("-q")) {
                    target = new Document(inputs.getLast());
                    break;
                }
                else if(input.equals("-f")){
                    target = new Document(new File(inputs.getLast()));
                    break;
                }
                documents.add(new Document(new File(input)));
            }
            termWeightMatrix = new TermWeightMatrix(target, documents);
            Map<Document, Double> result = termWeightMatrix.calculateCosineSimilarity();
            System.out.println();
            System.out.println("Query Terms : " + target.getTerms());
            System.out.println();
            System.out.println(
                    ("%" + (-20) + "s" + "%" + (-20) + "s" + "%s").formatted("Document Name", "Document Size", "Rank"));
            for (Map.Entry<Document, Double> e : result.entrySet()) {
                System.out.println(("%" + (-20) + "s" + "%d Unique terms  " + "%f").formatted(e.getKey().getName(),
                        e.getKey().getSize(), e.getValue()));
            }
        }

    }

    private static boolean containsOption(ArrayList<String> inputs) {
        ArrayList<String> options = new ArrayList<>(Arrays.asList("-f","-q"));
        System.out.println(inputs);
        for (String option : inputs) {
            if(!options.contains(option)||(inputs.indexOf(option) != (inputs.size()-2))
            || (inputs.indexOf(option) == 0)){
                continue;
            }
            else{
                return true;
            }
        }
        return false;
    }
}
