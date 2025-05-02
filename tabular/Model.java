package tabular;
/*
 * Author:
 * Date:
 * Purpose:
 */

import java.util.*;

public class Model {   
    private FeatureProcessor ham = new FeatureProcessor();
    private FeatureProcessor spam = new FeatureProcessor();

    private DataSet dataSet;
    private ArrayList<DataRow> trainingData;
    private ArrayList<DataRow> testingData;
    private Node headNode = new Node(); // head node for classification tree

    // initialize the model object
    public Model(DataSet ds) {
        this.dataSet = ds;
        this.trainingData = ds.getTrainingData();
        this.testingData = ds.getTestingData();
    }

    private Node calculateImpurities(String property, List<Integer> values) {
        double lowestImpurity = Double.MAX_VALUE;
        int bestThreshold = 0;
        Node daNode = new Node();

        for (int i = 1; i < values.size(); i++) {
            int threshold = (values.get(i - 1) + values.get(i)) / 2;
            double impurity = giniImpurity(threshold, property);

            if (impurity < lowestImpurity) {
                lowestImpurity = impurity;
                bestThreshold = threshold;
            }
        }

        daNode.setFeature(property);
        daNode.setThreshold(bestThreshold);
        daNode.setImpurity(lowestImpurity);

        return daNode;
    }

    /* Trains model
     * Equate the gini impurity and test it against all the properties in
     * respective feature processor (ham or spam)
     */
    public void trainModel() {
        // Process training emails
        for (DataRow row : this.trainingData) {
            if (row.getLabel().equalsIgnoreCase("spam")) {
                spam.processEmail(row.getEmail());
            } else {
                ham.processEmail(row.getEmail());
            }
        }

        // Combine and sort lengths
        ArrayList<Integer> allLengths = new ArrayList<>(spam.getAllLengths());
        allLengths.addAll(ham.getAllLengths());
        Collections.sort(allLengths);

        // Calculate impurities for lengths
        Node lengthNode = calculateImpurities("length", allLengths);

        // Combine and sort all avg word lengths
        ArrayList<Integer> allAvgWord = new ArrayList<Integer>(spam.allAvgWordLengths());
        allAvgWord.addAll(ham.allAvgWordLengths());
        Collections.sort(allAvgWord);

        // Calculate impurities for avg word length
        Node awlNode = calculateImpurities("avgWordLength", allAvgWord);

        Node otherNode;
        // Determine the head node
        if (lengthNode.getImpurity() < awlNode.getImpurity()) {
            headNode = lengthNode;
            otherNode = awlNode;
        } else {
            headNode = awlNode;
            otherNode = lengthNode;
        }
         /* Place the other node as a child of the head node
         * and classify it as spam or ham based on the threshold
         */
        /*** TODO: I'm pretty sure this is wrong but I don't know how to fix it ***/
        Node nullNode = new Node();
        if (otherNode.getThreshold() < headNode.getThreshold()) {
            headNode.setLeft(otherNode); // Left (true) child
            otherNode.setClassification("ham");
            headNode.setRight(nullNode);
            nullNode.setClassification("spam");
        } else {
            headNode.setRight(otherNode); // Right (false) child
            otherNode.setClassification("spam");
            headNode.setLeft(nullNode);
            nullNode.setClassification("ham");
        }
        /******************************************/
    }

    /* makes predictions with the rest of the known data 
     */
    public double predict() {
        int correct = 0;
        int totalPredicted = 0;

        for (DataRow row : testingData) {

            int prediction = predict(row.getEmail());

            if (prediction == (row.getX())) {
                correct++;
            }
            totalPredicted++;
        }

        return (double) correct / (double) totalPredicted;
    }

    // Make a prediction using a new email input 
    public int predict(String email) {
        Node currentNode = headNode;
        String classification;

        while (!currentNode.isLeaf()) {
            if (currentNode.getFeature().equals("length")) {
                if (email.length() < currentNode.getThreshold()) {
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode = currentNode.getRight();
                }
            } else if (currentNode.getFeature().equals("avgWordLength")) {
                double avgWordLength = (double) email.length() / email.split("\\s+").length;
                if (avgWordLength < currentNode.getThreshold()) {
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode = currentNode.getRight();
                }
            }
        }
        classification = currentNode.getClassification();

        if (classification.equals("spam")) {
            return 1;
        } else {
            return 0;
        }
    }

    
    private double giniImpurity(double val, String property) {
        int spamYes = 0;
        int spamNo = 0;
        int hamYes = 0;
        int hamNo = 0;
        double totalImpurity = 0.0;
        ArrayList<Integer> spamPoo;
        ArrayList<Integer> hamPoo;

        if (property.equals("length")) {
            spamPoo = spam.getAllLengths();
            hamPoo = ham.getAllLengths();
        }
        else {
            spamPoo = spam.allAvgWordLengths();
            hamPoo = ham.allAvgWordLengths();
        }
        for (int i : spamPoo) {
            if (i < val) {
                spamYes++;
            } else {
                spamNo++;
            }
        }
        for (int i : hamPoo) {
            if (i < val) {
                hamYes++;
            } else {
                hamNo++;
            }
        }

        double yesProb = ((double) spamYes / (double) (spamYes + spamNo));
        double noProb = ((double) spamNo / (double) (spamYes + spamNo));
        double spamGini = 1 - (yesProb * yesProb) - (noProb * noProb);

        yesProb = ((double) hamYes / (double) (spamYes + hamNo));
        noProb = ((double) hamNo / (double) (hamYes + hamNo));
        double hamGini = 1 - (yesProb * yesProb) - (noProb * noProb);
        
        // calculate the weighted average of the two gini impurities
        double total = spamYes + spamNo + hamYes + hamNo;
        if (total != 0) {
            totalImpurity = ((spamYes + spamNo) / total) * spamGini + ((hamYes + hamNo) / total) * hamGini;
            return totalImpurity;
        }
        return 0.0;
    }
}
