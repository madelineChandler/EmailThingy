package tabular;
/*
 * Author:
 * Date:
 * Purpose:
 */

import java.util.ArrayList;

public class Model {   
   private FeatureProcessor ham;
   private FeatureProcessor spam;
   private DataSet dataSet;
   private DataSet trainingData;
   private DataSet testingData;
   private ArrayList<Node> modelTree = new ArrayList<Node>();
   
   /* initialize the model object */
   public Model(DataSet ds) {
      this.dataSet = ds;
      this.trainingData = ds.getTrainingData();
      this.testingData = ds.getTestingData();
      
      ham = new FeatureProcessor();
      spam = new FeatureProcessor();
    }
    
    /* trains model 
     * equate the gini impurity and test it against all the properties in
     * respective feature processor (ham or spam)
     */
    public void trainModel() {
      
    }
    
    /* makes predictions with the rest of the known data (20%) and 
     * returns its accuracy 
     */
    public int predict() {
    
    }
    
    // make a prediction using a new email input (for funsies)
    public predict(String email) {
      
    }
    
    /* @param
     */
    private double calculateImpurity() {
      
    }
}
