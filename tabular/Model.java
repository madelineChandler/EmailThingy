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

    /* Trains model
     * Equate the gini impurity and test it against all the properties in
     * respective feature processor (ham or spam)
     */
    public void trainModel() {
        // Process training emails and categorize into ham or spam
        for (DataRow row : this.trainingData) {
            if (row.getLabel().equalsIgnoreCase("spam")) {
                spam.processEmail(row.getEmail());
            } else {
                ham.processEmail(row.getEmail());        
            }
        }       
        
        /* TODO: implement gini impurity to determine order to create modelTree */
        ArrayList<Integer> allLengths = spam.getAllLengths();
        ArrayList<Double> impurities = new ArrayList<Double>(allLengths.size()-1);

        for (int i = 1; i < spam.getEmailCount(); i++) {
            int avgLength = (allLengths.get(i-1) + allLengths.get(i)) / 2;
            impurities.add(giniImpurity(avgLength, spam));
        }
    }

    /* makes predictions with the rest of the known data 
     */
    public double predict() {
        int correct = 0;
        int totalPredicted = 0;

        for (DataRow row : testingData) {
            
            double avgLength = row.getEmail().split("\\s+").length == 0 ? 0 :
                Arrays.stream(row.getEmail().split("\\s+")).mapToInt(String::length).average().orElse(0);

            double spamAvg = spam.getAverageWordLength();
            double hamAvg = ham.getAverageWordLength();

            String prediction = Math.abs(avgLength - spamAvg) < Math.abs(avgLength - hamAvg) ? "spam" : "ham";

            if (prediction.equalsIgnoreCase(row.getLabel())) {
                correct++;
            }
            totalPredicted++;
        }

        return (double) correct / (double) totalPredicted;
    }

    // Make a prediction using a new email input 
    public int predict(String email) {
        double avgLength = email.split("\\s+").length == 0 ? 0 :
            Arrays.stream(email.split("\\s+")).mapToInt(String::length).average().orElse(0);

        double spamAvg = spam.getAverageWordLength();
        double hamAvg = ham.getAverageWordLength();

        return Math.abs(avgLength - spamAvg) < Math.abs(avgLength - hamAvg) ? 1 : 0; // 1 = spam, 0 = ham
    }

    
    private double giniImpurity(double property, FeatureProcessor processor) {
        // TODO:Maddie

        return 0.0;
    }
}
