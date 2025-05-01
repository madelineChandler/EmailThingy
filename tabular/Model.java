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
    private ArrayList<Node> modelTree = new ArrayList<>(); // tree of conditional nodes (for predicting)

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
        for (DataRow row : trainingData) {
            if (row.getLabel().equalsIgnoreCase("spam")) {
                spam.processEmail(row.getEmail());
            } else {
                ham.processEmail(row.getEmail());
            }
        }

        // Extract all words to update top words
        List<String> hamWords = new ArrayList<>();
        List<String> spamWords = new ArrayList<>();

        for (DataRow row : trainingData) {
            String[] words = row.getEmail().split("\\s+");
            if (row.getLabel().equalsIgnoreCase("spam")) {
                spamWords.addAll(Arrays.asList(words));
            } else {
                hamWords.addAll(Arrays.asList(words));
            }
        }

        spam.updateWordList(spamWords);
        ham.updateWordList(hamWords);

       
        
    }

    /* makes predictions with the rest of the known data 
     */
    public int predict() {
        int correct = 0;

        for (DataRow row : testingData) {
            
            double avgLength = row.getEmail().split("\\s+").length == 0 ? 0 :
                Arrays.stream(row.getEmail().split("\\s+")).mapToInt(String::length).average().orElse(0);

            double spamAvg = spam.getAverageWordLength();
            double hamAvg = ham.getAverageWordLength();

            String prediction = Math.abs(avgLength - spamAvg) < Math.abs(avgLength - hamAvg) ? "spam" : "ham";

            if (prediction.equalsIgnoreCase(row.getLabel())) {
                correct++;
            }
        }

        return correct;
    }

    // Make a prediction using a new email input 
    public int predict(String email) {
        double avgLength = email.split("\\s+").length == 0 ? 0 :
            Arrays.stream(email.split("\\s+")).mapToInt(String::length).average().orElse(0);

        double spamAvg = spam.getAverageWordLength();
        double hamAvg = ham.getAverageWordLength();

        return Math.abs(avgLength - spamAvg) < Math.abs(avgLength - hamAvg) ? 1 : 0; // 1 = spam, 0 = ham
    }

    
    private double giniImpurity() {
        // TODO:Maddie
        return 0.0;
    }
}
