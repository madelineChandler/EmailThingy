package tabular;

import java.util.*;

public class Model {
    private FeatureProcessor ham = new FeatureProcessor();
    private FeatureProcessor spam = new FeatureProcessor();

    private DataSet dataSet;
    private ArrayList<DataRow> trainingData;
    private ArrayList<DataRow> testingData;
    private Node headNode = new Node(); // root node

    public Model(DataSet ds) {
        this.dataSet = ds;
        this.trainingData = ds.getTrainingData();
        this.testingData = ds.getTestingData();
    }

    private Node calculateImpurities(String property, List<Integer> values) {
        double lowestImpurity = Double.MAX_VALUE;
        double bestThreshold = 0.0;
        Node daNode = new Node();

        for (int i = 1; i < values.size(); i++) {
            double threshold = (values.get(i - 1) + values.get(i)) / 2.0; // avg of two adjacent values
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

        // Feature: length
        ArrayList<Integer> allLengths = new ArrayList<>(spam.getAllLengths());
        allLengths.addAll(ham.getAllLengths());
        Collections.sort(allLengths);
        Node lengthNode = calculateImpurities("length", allLengths);

        // Feature: average word length
        ArrayList<Integer> allAvgWord = new ArrayList<>(spam.getAllAvgWordLengths());
        allAvgWord.addAll(ham.getAllAvgWordLengths());
        Collections.sort(allAvgWord);
        Node awlNode = calculateImpurities("avgWordLength", allAvgWord);

        // Features: uppercase and lowercase counts
        ArrayList<Integer> upperCounts = new ArrayList<>();
        ArrayList<Integer> lowerCounts = new ArrayList<>();

        LetterCounter counter = new LetterCounter();

        for (DataRow row : trainingData) {
            counter.reset();
            counter.countLetters(row.getEmail());
            upperCounts.add(counter.getUppercaseCount());
            lowerCounts.add(counter.getLowercaseCount());
        }

        Collections.sort(upperCounts);
        Collections.sort(lowerCounts);

        Node upperNode = calculateImpurities("uppercase", upperCounts);
        Node lowerNode = calculateImpurities("lowercase", lowerCounts);

        // Choose the best node
        Node bestNode = lengthNode;
        Node nextBestNode = lengthNode;
        Node thirdBestNode = lengthNode;
        Node[] nodes = {awlNode, upperNode, lowerNode, lengthNode};
        int i = 0;
        for (; i < nodes.length; i++) {
            if (nodes[i].getImpurity() < bestNode.getImpurity())
                bestNode = nodes[i];
        }
        for (i = 0; i < nodes.length; i++) {
            if (nodes[i].getImpurity() < nextBestNode.getImpurity() && nodes[i] != bestNode)
                nextBestNode = nodes[i];
        }
        for (i = 0; i < nodes.length; i++) {
            if (nodes[i].getImpurity() < thirdBestNode.getImpurity() && nodes[i] != bestNode &&
            nodes[i] != nextBestNode)
                thirdBestNode = nodes[i];
        }

        headNode = bestNode;
        headNode.setLeft(nextBestNode);
        Node rightLeaf = new Node();
        rightLeaf.setClassification("spam");
        headNode.setRight(rightLeaf);

        thirdBestNode.setClassification("ham");
        nextBestNode.setLeft(thirdBestNode);
        Node rightLeaf2 = new Node();
        rightLeaf2.setClassification("spam");
        nextBestNode.setRight(rightLeaf2);

        Node leftLeaf = new Node();
        leftLeaf.setClassification("ham");
        thirdBestNode.setLeft(leftLeaf);
        Node rightLeaf3 = new Node();
        rightLeaf3.setClassification("spam");
        thirdBestNode.setRight(rightLeaf3);
    }

    public double predict() {
        int correct = 0;
        int totalPredicted = 0;

        for (DataRow row : testingData) {
            int prediction = predict(row.getEmail());

            if (prediction == row.getX()) { // Fixed method reference
                correct++;
            }
            totalPredicted++;
        }

        return (double) correct / totalPredicted;
    }

    public int predict(String email) {
        Node currentNode = headNode;

        while (!currentNode.isLeaf()) {
            String feature = currentNode.getFeature();
            double threshold = currentNode.getThreshold();

            switch (feature) {
                case "length":
                    currentNode = email.length() < threshold ? currentNode.getLeft() : currentNode.getRight();
                    break;
                case "avgWordLength":
                    double avgWordLength = (double) email.length() / email.split("\\s+").length;
                    currentNode = avgWordLength < threshold ? currentNode.getLeft() : currentNode.getRight();
                    break;
                case "uppercase":
                    LetterCounter upperCounter = new LetterCounter();
                    upperCounter.countLetters(email);
                    currentNode = upperCounter.getUppercaseCount() < threshold ? currentNode.getLeft() : currentNode.getRight();
                    break;
                case "lowercase":
                    LetterCounter lowerCounter = new LetterCounter();
                    lowerCounter.countLetters(email);
                    currentNode = lowerCounter.getLowercaseCount() < threshold ? currentNode.getLeft() : currentNode.getRight();
                    break;
                default:
                    return 0;
            }
        }

        return currentNode.getClassification().equals("spam") ? 1 : 0;
    }

    private double giniImpurity(double val, String property) {
        int spamYes = 0, spamNo = 0, hamYes = 0, hamNo = 0;

        ArrayList<Integer> spamPoo = property.equals("length") ? spam.getAllLengths() :
                property.equals("avgWordLength") ? spam.getAllAvgWordLengths() :
                        getLetterCounts(spam, property);
        ArrayList<Integer> hamPoo = property.equals("length") ? ham.getAllLengths() :
                property.equals("avgWordLength") ? ham.getAllAvgWordLengths() :
                        getLetterCounts(ham, property);

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

        return ((double) totalYes / (totalYes + totalNo)) * giniYes +
                ((double) totalNo / (totalYes + totalNo)) * giniNo;
    }

    private ArrayList<Integer> getLetterCounts(FeatureProcessor processor, String type) {
        ArrayList<String> emails = processor.getEmails(); // Ensure this method exists in FeatureProcessor
        ArrayList<Integer> counts = new ArrayList<>();
        LetterCounter counter = new LetterCounter();

        for (String email : emails) {
            counter.reset();
            counter.countLetters(email);
            counts.add(type.equals("uppercase") ? counter.getUppercaseCount() : counter.getLowercaseCount());
        }

        return counts;
    }
}