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
        Node lengthNode = calculateImpurities("length", allLengths);

        // Combine and sort all avg word lengths
        ArrayList<Integer> allAvgWord = new ArrayList<>(spam.allAvgWordLengths());
        allAvgWord.addAll(ham.allAvgWordLengths());
        Collections.sort(allAvgWord);
        Node awlNode = calculateImpurities("avgWordLength", allAvgWord);

        // Determine head node based on lower impurity
        if (lengthNode.getImpurity() < awlNode.getImpurity()) {
            headNode = lengthNode;

            Node left = new Node();
            left.setFeature("avgWordLength");
            left.setThreshold(awlNode.getThreshold());
            Node leftLeft = new Node();
            leftLeft.setClassification("ham");
            Node leftRight = new Node();
            leftRight.setClassification("spam");
            left.setLeft(leftLeft);
            left.setRight(leftRight);

            headNode.setLeft(left);

            Node rightLeaf = new Node();
            rightLeaf.setClassification("spam");
            headNode.setRight(rightLeaf);
        } else {
            headNode = awlNode;

            Node left = new Node();
            left.setFeature("length");
            left.setThreshold(lengthNode.getThreshold());
            Node leftLeft = new Node();
            leftLeft.setClassification("ham");
            Node leftRight = new Node();
            leftRight.setClassification("spam");
            left.setLeft(leftLeft);
            left.setRight(leftRight);

            headNode.setLeft(left);

            Node rightLeaf = new Node();
            rightLeaf.setClassification("spam");
            headNode.setRight(rightLeaf);
        }
    }

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
        return classification.equals("spam") ? 1 : 0;
    }

    private double giniImpurity(double val, String property) {
        int spamYes = 0, spamNo = 0, hamYes = 0, hamNo = 0;
        double totalImpurity;

        ArrayList<Integer> spamPoo = property.equals("length") ? spam.getAllLengths() : spam.allAvgWordLengths();
        ArrayList<Integer> hamPoo = property.equals("length") ? ham.getAllLengths() : ham.allAvgWordLengths();

        for (int i : spamPoo) {
            if (i < val) spamYes++;
            else spamNo++;
        }

        for (int i : hamPoo) {
            if (i < val) hamYes++;
            else hamNo++;
        }

        int totalYes = spamYes + hamYes;
        int totalNo = spamNo + hamNo;

        double giniYes = 1.0;
        if (totalYes > 0) {
            double spamYesProb = (double) spamYes / totalYes;
            double hamYesProb = (double) hamYes / totalYes;
            giniYes = 1 - (spamYesProb * spamYesProb + hamYesProb * hamYesProb);
        }

        double giniNo = 1.0;
        if (totalNo > 0) {
            double spamNoProb = (double) spamNo / totalNo;
            double hamNoProb = (double) hamNo / totalNo;
            giniNo = 1 - (spamNoProb * spamNoProb + hamNoProb * hamNoProb);
        }

        double total = totalYes + totalNo;
        totalImpurity = ((double) totalYes / total) * giniYes + ((double) totalNo / total) * giniNo;

        return totalImpurity;
    }
}
