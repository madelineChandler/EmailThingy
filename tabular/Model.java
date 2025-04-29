package tabular;
/*
 * Author:
 * Date:
 * Purpose:
 */

import java.util.ArrayList;

public class Model {   
   private FeatureProcessor ham = new FeatureProcessor();
   private FeatureProcessor spam = new FeatureProcessor();

   private DataSet dataSet;
   private ArrayList<DataRow> trainingData;
   private ArrayList<DataRow> testingData;
   private ArrayList<Node> modelTree = new ArrayList<Node>(); // tree of conditional nodes (for predicting)
   
   /* initialize the model object */
   public Model(DataSet ds) {
      this.dataSet = ds;
      this.trainingData = ds.getTrainingData();
      this.testingData = ds.getTestingData();
    }
    
    /* Trains model
     * equate the gini impurity and test it against all the properties in
     * respective feature processor (ham or spam)
     */
    public void trainModel() {
      /* TODO: implement (you can just call giniImpurity() for now bc its just math;
          i'll fix up giniImpurity() later).
           ngl i'm hella eepy but if you need specification just look up gini impurity and decision trees.
           you can also ask me tomorrow for clarification if you want.
            I really like these videos: "https://www.youtube.com/watch?v=_L39rN6gz7Y" & "https://www.youtube.com/watch?v=ZVR2Way4nwQ"*/
    }
    
    /* makes predictions with the rest of the known data (testingData)
     * returns its accuracy 
     */
    public int predict() {
        /* TODO: implement using modelTree */
        return 0;
    }
    
    // make a prediction using a new email input (for funsies)
    public int predict(String email) {
        /* TODO: implement (LOW PRIORITY) */
        return 0;
    }
    
    /* @param
     */
    private double giniImpurity() {
        /* TODO: implement (Maddie can do this later)*/
        return 0.0;
    }
}
