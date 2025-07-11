/*
 * Author: Madeline Chandler
 * Date: 5/1/2025
 */

import java.util.ArrayList;
import tabular.*;
import gui.*;

public class ModelDemo {
    public static void main(String[] args) {
      MainFrame frame = new MainFrame();
      
      String fileName = "spam_or_not_spam.csv";
      DataSet ds = new DataSet(fileName);
      Model model = new Model(ds);

      System.out.println("Dataset: " + fileName);
      System.out.println("Training Data: " + ds.getTrainingData().size());
      System.out.println("Testing Data: " + ds.getTestingData().size());
      System.out.println("------------------------");
      System.out.println("Training Model... this may take about a minute.");
      model.trainModel();
      System.out.println("Training complete.");
      System.out.println("Accuracy: " + model.predict());

      frame.initialize(model); // initializes gui
    }
}
